package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.PiggyBank;
import innohackatons.entity.User;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DirtiesContext
public class PiggyBankRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private PiggyBankRepository piggyBankRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatPiggyBankWorksCorrectly() {
        User user = new User().setName("Test User");

        user = userRepository.save(user);

        String goal = "Test Goal";

        BigDecimal amount = new BigDecimal("100.00");
        PiggyBank piggyBank = new PiggyBank()
            .setUser(user)
            .setAmount(amount)
            .setGoal(goal);
        piggyBank = piggyBankRepository.save(piggyBank);

        assertThat(piggyBank.getId()).isNotNull();

        Optional<PiggyBank> savedPiggyBankOptional = piggyBankRepository.findById(piggyBank.getId());
        assertThat(savedPiggyBankOptional).isPresent();

        PiggyBank savedPiggyBank = savedPiggyBankOptional.get();
        assertThat(savedPiggyBank.getUser()).isEqualTo(user);
        assertThat(savedPiggyBank.getAmount()).isEqualByComparingTo(amount);
        assertThat(savedPiggyBank.getGoal()).isEqualTo(goal);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdatePiggyBankWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        String goal = "Test Goal";
        BigDecimal amount = new BigDecimal("100.00");
        PiggyBank piggyBank = new PiggyBank()
            .setUser(user)
            .setAmount(amount)
            .setGoal(goal);
        piggyBank = piggyBankRepository.save(piggyBank);

        BigDecimal updatedAmount = new BigDecimal("200.00");
        piggyBank.setAmount(updatedAmount);
        piggyBankRepository.save(piggyBank);

        Optional<PiggyBank> updatedPiggyBankOptional = piggyBankRepository.findById(piggyBank.getId());
        assertThat(updatedPiggyBankOptional).isPresent();
        assertThat(updatedPiggyBankOptional.get().getAmount()).isEqualByComparingTo(updatedAmount);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeletePiggyBankWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        String goal = "Test Goal";
        BigDecimal amount = new BigDecimal("100.00");
        PiggyBank piggyBank = new PiggyBank()
            .setUser(user)
            .setAmount(amount)
            .setGoal(goal);
        piggyBank = piggyBankRepository.save(piggyBank);

        piggyBankRepository.delete(piggyBank);

        assertThat(piggyBankRepository.findById(piggyBank.getId())).isEmpty();
    }

}

