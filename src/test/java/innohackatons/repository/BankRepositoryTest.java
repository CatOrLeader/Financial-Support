package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.configuration.kafka.KafkaConfiguration;
import innohackatons.kafka.TransactionConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
class BankRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private BankRepository bankRepository;
    @MockBean
    private KafkaConfiguration kafkaConfiguration;
    @MockBean
    private TransactionConsumer transactionConsumer;

    @Test
    @Transactional
    @Rollback
    void assertThatAddBankWorksCorrectly() {

        assertThat(bankRepository.findById(1L)).isPresent();
    }
}
