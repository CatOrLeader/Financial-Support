package innohackatons.service.implementation;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final CategoryRepository categoryRepository;
    private final DepositRepository depositRepository;

    @Override
    public ResponseEntity<TransactionResponse> processTransaction(PostTransactionRequest request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new NotFoundEntityException("User not found"));
        Bank bank = bankRepository.findById(request.bankId())
            .orElseThrow(() -> new NotFoundEntityException("Bank not found"));
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new NotFoundEntityException("Category not found"));
        Deposit deposit = depositRepository.findByUserIdAndBankId(user.getId(), bank.getId())
            .orElseThrow(() -> new NotFoundEntityException("Deposit not found"));

        BigDecimal newBalance = deposit.getAmount().add(BigDecimal.valueOf(request.amount()));

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ConflictException("Insufficient funds");
        } else {
            deposit.setAmount(newBalance);
            depositRepository.save(deposit);

            Transaction transaction = new Transaction()
                .setUser(user)
                .setBank(bank)
                .setCategory(category)
                .setAmount(BigDecimal.valueOf(request.amount()))
                .setDate(LocalDateTime.now());
            transactionRepository.save(transaction);

            TransactionResponse response = new TransactionResponse(LocalDateTime.now());
            return ResponseEntity.ok(response);
        }
    }
}
