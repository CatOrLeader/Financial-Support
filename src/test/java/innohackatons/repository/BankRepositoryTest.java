package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class BankRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private BankRepository bankRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatAddBankWorksCorrectly() {

        assertThat(bankRepository.findById(1L)).isPresent();
    }
}
