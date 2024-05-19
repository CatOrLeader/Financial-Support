package innohackatons.service.implementation;

import innohackatons.IntegrationEnvironment;
import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.PostTransactionRequest;
import innohackatons.api.model.TransactionResponse;
import innohackatons.entity.Bank;
import innohackatons.entity.Category;
import innohackatons.entity.Deposit;
import innohackatons.entity.Transaction;
import innohackatons.entity.User;
import innohackatons.repository.BankRepository;
import innohackatons.repository.CategoryRepository;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.TransactionRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceImplTest extends IntegrationEnvironment {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BankRepository bankRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private DepositRepository depositRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    private User user;
    private Bank bank;
    private Category category;
    private Deposit deposit;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        bank = new Bank();
        bank.setId(1L);

        category = new Category();
        category.setId(1L);

        deposit = new Deposit();
        deposit.setUser(user);
        deposit.setBank(bank);
        deposit.setAmount(BigDecimal.valueOf(-100));
    }

    @Test
    @Order(1)
    void processTransaction_whenAllCorrect_thenTransactionProcessed() {
        PostTransactionRequest request = new PostTransactionRequest(1L, 1L, 1L, 50.0, LocalDateTime.now());

        Mockito.when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));
        Mockito.when(bankRepository.findById(request.bankId())).thenReturn(Optional.of(bank));
        Mockito.when(categoryRepository.findById(request.categoryId())).thenReturn(Optional.of(category));
        Mockito.when(depositRepository.findByUserIdAndBankId(user.getId(), bank.getId())).thenReturn(Optional.of(deposit));
        deposit.setAmount(BigDecimal.valueOf(100));
        ResponseEntity<TransactionResponse> response = transactionService.processTransaction(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().proceededDate()).isNotNull();

        Mockito.verify(depositRepository).save(deposit);
        Mockito.verify(transactionRepository).save(Mockito.any(Transaction.class));
    }

    @Test
    @Order(2)
    void processTransaction_whenUserNotFound_thenNotFoundException() {
        PostTransactionRequest request = new PostTransactionRequest(1L, 1L, 1L, 50.0, LocalDateTime.now());

        Mockito.when(userRepository.findById(request.userId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> transactionService.processTransaction(request))
            .withMessage("User not found");
    }

    @Test
    @Order(3)
    void processTransaction_whenBankNotFound_thenNotFoundException() {
        PostTransactionRequest request = new PostTransactionRequest(1L, 1L, 1L, 50.0, LocalDateTime.now());

        Mockito.when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));
        Mockito.when(bankRepository.findById(request.bankId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> transactionService.processTransaction(request))
            .withMessage("Bank not found");
    }

    @Test
    @Order(4)
    void processTransaction_whenCategoryNotFound_thenNotFoundException() {
        PostTransactionRequest request = new PostTransactionRequest(1L, 1L, 1L, 50.0, LocalDateTime.now());

        Mockito.when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));
        Mockito.when(bankRepository.findById(request.bankId())).thenReturn(Optional.of(bank));
        Mockito.when(categoryRepository.findById(request.categoryId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> transactionService.processTransaction(request))
            .withMessage("Category not found");
    }

    @Test
    @Order(5)
    void processTransaction_whenDepositNotFound_thenNotFoundException() {
        PostTransactionRequest request = new PostTransactionRequest(1L, 1L, 1L, 50.0, LocalDateTime.now());

        Mockito.when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));
        Mockito.when(bankRepository.findById(request.bankId())).thenReturn(Optional.of(bank));
        Mockito.when(categoryRepository.findById(request.categoryId())).thenReturn(Optional.of(category));
        Mockito.when(depositRepository.findByUserIdAndBankId(user.getId(), bank.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> transactionService.processTransaction(request))
            .withMessage("Deposit not found");
    }

    @Test
    @Order(6)
    void processTransaction_whenInsufficientFunds_thenConflictException() {
        PostTransactionRequest request = new PostTransactionRequest(1L, 1L, 1L, 50.0, LocalDateTime.now());

        Mockito.when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));
        Mockito.when(bankRepository.findById(request.bankId())).thenReturn(Optional.of(bank));
        Mockito.when(categoryRepository.findById(request.categoryId())).thenReturn(Optional.of(category));
        Mockito.when(depositRepository.findByUserIdAndBankId(user.getId(), bank.getId())).thenReturn(Optional.of(deposit));

        assertThatExceptionOfType(ConflictException.class)
            .isThrownBy(() -> transactionService.processTransaction(request))
            .withMessage("Insufficient funds");
    }
}
