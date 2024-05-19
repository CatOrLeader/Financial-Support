package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.configuration.kafka.KafkaConfiguration;
import innohackatons.entity.Bank;
import innohackatons.entity.Cashback;
import innohackatons.entity.CashbackId;
import innohackatons.entity.Category;
import innohackatons.entity.User;
import innohackatons.kafka.TransactionConsumer;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@ExtendWith(MockitoExtension.class)
class CashbackRepositoryTest extends IntegrationEnvironment {

    @MockBean
    private KafkaConfiguration kafkaConfiguration;
    @MockBean
    private TransactionConsumer transactionConsumer;

    @Autowired
    private CashbackRepository cashbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @Rollback
    void assertThatAddCashbackWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Category category = categoryRepository.save(new Category().setId(1L).setCategoryName("hackatons"));

        CashbackId cashbackId = new CashbackId(user.getId(), bank.getId(), category.getId());
        Cashback cashback = new Cashback()
            .setId(cashbackId)
            .setCategory(category)
            .setBank(bank)
            .setRatio(new BigDecimal("14.00"));

        cashbackRepository.save(cashback);

        assertThat(cashbackRepository.findById(cashbackId)).isPresent();
    }

}
