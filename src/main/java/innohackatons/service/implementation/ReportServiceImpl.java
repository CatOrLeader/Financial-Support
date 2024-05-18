package innohackatons.service.implementation;

import innohackatons.entity.Transaction;
import innohackatons.service.ReportService;
import innohackatons.service.dto.CategoryReportDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("MagicNumber")
public class ReportServiceImpl implements ReportService {
    @Override
    public CategoryReportDTO generateCategoryReport(
        Long categoryId,
        Long userId,
        LocalDateTime dateFrom,
        LocalDateTime dateTo,
        List<Transaction> transactions
    ) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if ((transaction.getDate().isAfter(dateFrom) && transaction.getDate().isBefore(dateTo))
                || transaction.getDate().equals(dateFrom) || transaction.getDate().equals(dateTo)) {
                if (Objects.equals(transaction.getCategory().getCategoryId(), categoryId)
                    && transaction.getUser().getId().equals(userId)) {
                    filteredTransactions.add(transaction);
                }
            }
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCashback = BigDecimal.ZERO;
        BigDecimal potentialProfit = BigDecimal.ZERO;

        //TODO get current cashback from each bank
        HashMap<Long, Double> bankCashback = new HashMap<>();
        bankCashback.put(1L, 0.1);
        bankCashback.put(2L, 0.2);
        bankCashback.put(3L, 0.15);

        //TODO get best cashback ratio
        Long optimalBankId = bankCashback.keySet().iterator().next();
        Double optimalCashback = null;
        for (Long bankUUID : bankCashback.keySet()) {
            if (optimalCashback == null) {
                optimalBankId = bankUUID;
                optimalCashback = bankCashback.get(bankUUID);
            } else if (optimalCashback < bankCashback.get(bankUUID)) {
                optimalBankId = bankUUID;
                optimalCashback = bankCashback.get(bankUUID);
            }
        }

        for (Transaction transaction : filteredTransactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
            totalCashback = totalCashback.add(transaction.getAmount()
                .multiply(BigDecimal.valueOf(bankCashback.get(transaction.getBank().getId()))));
            potentialProfit =
                potentialProfit.add(transaction.getAmount().multiply(BigDecimal.valueOf(optimalCashback)));
        }

        return new CategoryReportDTO(
            categoryId,
            dateFrom,
            dateTo,
            optimalBankId,
            totalAmount,
            totalCashback,
            potentialProfit
        );
    }
}
