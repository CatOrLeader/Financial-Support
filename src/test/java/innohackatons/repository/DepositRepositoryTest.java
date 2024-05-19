package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.Bank;
import innohackatons.entity.Deposit;
import innohackatons.entity.User;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class DepositRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatAddDepositWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Deposit deposit = new Deposit()
            .setUser(user)
            .setBank(bank)
            .setAmount(new BigDecimal("1000.00"));

        deposit = depositRepository.save(deposit);

        assertThat(depositRepository.findById(deposit.getId())).isPresent();
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateDepositWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Deposit deposit = new Deposit()
            .setUser(user)
            .setBank(bank)
            .setAmount(new BigDecimal("1000.00"));
        deposit = depositRepository.save(deposit);

        BigDecimal updatedAmount = new BigDecimal("2000.00");
        deposit.setAmount(updatedAmount);
        depositRepository.save(deposit);

        Optional<Deposit> updatedDepositOptional = depositRepository.findById(deposit.getId());
        assertThat(updatedDepositOptional).isPresent();
        assertThat(updatedDepositOptional.get().getAmount()).isEqualByComparingTo(updatedAmount);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteDepositWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Deposit deposit = new Deposit()
            .setUser(user)
            .setBank(bank)
            .setAmount(new BigDecimal("1000.00"));
        deposit = depositRepository.save(deposit);

        depositRepository.delete(deposit);

        assertThat(depositRepository.findById(deposit.getId())).isEmpty();
    }

}

