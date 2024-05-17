package innohackatons.repository;


import innohackatons.IntegrationEnvironment;
import java.math.BigDecimal;
import innohackatons.entity.Bank;
import innohackatons.entity.Deposit;
import innohackatons.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        Bank bank = bankRepository.save(new Bank().setBankName("Test Bank"));

        Deposit deposit = new Deposit()
            .setUser(user)
            .setBank(bank)
            .setAmount(new BigDecimal("1000.00"));

        deposit = depositRepository.save(deposit);

        assertThat(depositRepository.findById(deposit.getId())).isPresent();
    }
}

