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
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
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

        // Проверяем, что запись была успешно добавлена
        assertThat(piggyBank.getId()).isNotNull();

        // Читаем запись из репозитория и проверяем ее
        Optional<PiggyBank> savedPiggyBankOptional = piggyBankRepository.findById(piggyBank.getId());
        assertThat(savedPiggyBankOptional).isPresent();

        PiggyBank savedPiggyBank = savedPiggyBankOptional.get();
        assertThat(savedPiggyBank.getUser()).isEqualTo(user);
        assertThat(savedPiggyBank.getAmount()).isEqualByComparingTo(amount);
        assertThat(savedPiggyBank.getGoal()).isEqualTo(goal);
    }
}

