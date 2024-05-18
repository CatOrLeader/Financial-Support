package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.Bank;
import innohackatons.entity.Category;
import innohackatons.entity.Transaction;
import innohackatons.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransactionRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatSaveTransactionWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Category category = categoryRepository.save(new Category().setCategoryName("Test Category"));

        Transaction transaction = new Transaction()
            .setUser(user)
            .setBank(bank)
            .setCategory(category)
            .setAmount(new BigDecimal("1000.00"))
            .setDate(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        assertThat(transactionRepository.findById(transaction.getId())).isPresent();
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateTransactionWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Category category = categoryRepository.save(new Category().setCategoryName("Test Category"));

        Transaction transaction = new Transaction()
            .setUser(user)
            .setBank(bank)
            .setCategory(category)
            .setAmount(new BigDecimal("1000.00"))
            .setDate(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        BigDecimal updatedAmount = new BigDecimal("2000.00");
        transaction.setAmount(updatedAmount);
        transactionRepository.save(transaction);

        Optional<Transaction> updatedTransactionOptional = transactionRepository.findById(transaction.getId());
        assertThat(updatedTransactionOptional).isPresent();
        assertThat(updatedTransactionOptional.get().getAmount()).isEqualByComparingTo(updatedAmount);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteTransactionWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Category category = categoryRepository.save(new Category().setCategoryName("Test Category"));

        Transaction transaction = new Transaction()
            .setUser(user)
            .setBank(bank)
            .setCategory(category)
            .setAmount(new BigDecimal("1000.00"))
            .setDate(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        transactionRepository.delete(transaction);

        assertThat(transactionRepository.findById(transaction.getId())).isEmpty();
    }
}
