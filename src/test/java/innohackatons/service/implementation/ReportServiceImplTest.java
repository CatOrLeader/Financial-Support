package innohackatons.service.implementation;

import innohackatons.configuration.kafka.KafkaConfiguration;
import innohackatons.entity.Bank;
import innohackatons.entity.Category;
import innohackatons.entity.Transaction;
import innohackatons.entity.User;
import innohackatons.kafka.TransactionConsumer;
import innohackatons.service.ReportService;
import innohackatons.service.dto.CategoryReportDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReportServiceImpl.class)
@DirtiesContext
class ReportServiceImplTest {

    @Autowired
    private ReportService reportService;

    private List<Transaction> transactions;
    private Category category;
    private User user;
    private Bank bank1;
    private Bank bank2;

    @MockBean
    private KafkaConfiguration kafkaConfiguration;
    @MockBean
    private TransactionConsumer transactionConsumer;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);

        category = new Category();
        category.setId(1L);

        bank1 = new Bank();
        bank1.setId(1L);

        bank2 = new Bank();
        bank2.setId(2L);

        transactions = Arrays.asList(
            new Transaction()
                .setId(1L)
                .setUser(user)
                .setCategory(category)
                .setBank(bank1)
                .setAmount(new BigDecimal("100.00"))
                .setDate(LocalDateTime.of(2023, 1, 1, 12, 0)),
            new Transaction()
                .setId(2L)
                .setUser(user)
                .setCategory(category)
                .setBank(bank2)
                .setAmount(new BigDecimal("200.00"))
                .setDate(LocalDateTime.of(2023, 1, 2, 12, 0)),
            new Transaction()
                .setId(3L)
                .setUser(user)
                .setCategory(category)
                .setBank(bank1)
                .setAmount(new BigDecimal("300.00"))
                .setDate(LocalDateTime.of(2023, 1, 3, 12, 0))
        );
    }

    @Test
    void testGenerateCategoryReport() {
        LocalDateTime dateFrom = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(2023, 1, 3, 23, 59);

        CategoryReportDTO report = reportService.generateCategoryReport(
            category.getId(),
            user.getId(),
            dateFrom,
            dateTo,
            transactions
        );

        assertThat(report).isNotNull();
        assertThat(report.getCategoryId()).isEqualTo(category.getId());
        assertThat(report.getDateFrom()).isEqualTo(dateFrom);
        assertThat(report.getDateTo()).isEqualTo(dateTo);
        assertThat(report.getPotentialProfit()).isEqualByComparingTo(new BigDecimal("120.00")); // 600 * 0.2 (optimal cashback ratio)

    }
}
