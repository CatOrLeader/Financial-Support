package innohackatons.service.implementation;

import innohackatons.entity.Bank;
import innohackatons.entity.Category;
import innohackatons.entity.Transaction;
import innohackatons.entity.User;
import innohackatons.repository.CashbackRepository;
import innohackatons.repository.CategoryRepository;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.TransactionRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.ReportService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReportServiceImpl.class)
@DirtiesContext
class ReportServiceImplTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private CashbackRepository cashbackRepository;

    @MockBean
    private DepositRepository depositRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    private List<Transaction> transactions;
    private Category category;
    private User user;
    private Bank bank1;
    private Bank bank2;

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

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Mockito.when(transactionRepository
                .findTransactionsByUserIdAndCategoryIdAndDateRange(user.getId(), category.getId(), dateFrom, dateTo))
            .thenReturn(transactions);

//        GetCategoryReportResponse report = reportService.generateCategoryReport(
//            user.getId(),
//            new GetCategoryReportRequest(category.getId(), dateFrom, dateTo)
//        ).getBody();

//        assertThat(report).isNotNull();
//        assertThat(report.categoryName()).isEqualTo(category.getCategoryName());
//        assertThat(report.dateFrom()).isEqualTo(dateFrom);
//        assertThat(report.dateTo()).isEqualTo(dateTo);
//        assertThat(report.potentialProfit()).isEqualByComparingTo(new BigDecimal("120.00")); // 600 * 0.2
        // (optimal cashback ratio)
    }
}
