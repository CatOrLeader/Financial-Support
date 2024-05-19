package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.Bank;
import innohackatons.entity.Cashback;
import innohackatons.entity.CashbackId;
import innohackatons.entity.Category;
import innohackatons.entity.User;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class CashbackRepositoryTest extends IntegrationEnvironment {

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
    public void assertThatAddCashbackWorksCorrectly() {
        User user = userRepository.save(new User().setName("Test User"));
        Bank bank = bankRepository.findById(1L).orElse(null);
        Category category = categoryRepository.save(new Category().setId(1L).setCategoryName("hackatons"));

        CashbackId cashbackId = new CashbackId(user.getId(), bank.getId(), category.getId());
        Cashback cashback = new Cashback()
            .setId(cashbackId)
            .setCategory(category)
            .setBank(bank)
            .setRatio(new BigDecimal("14.00"));

        cashback = cashbackRepository.save(cashback);

        assertThat(cashbackRepository.findById(cashbackId)).isPresent();
    }

}
