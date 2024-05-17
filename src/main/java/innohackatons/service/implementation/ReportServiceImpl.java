package innohackatons.service.implementation;

import innohackatons.dto.CategoryReportDTO;
import innohackatons.dto.TransactionDTO;
import innohackatons.service.ReportService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public CategoryReportDTO generateCategoryReport(
        UUID categoryId,
        UUID userId,
        OffsetDateTime dateFrom,
        OffsetDateTime dateTo,
        List<TransactionDTO> transactions
    ) {
        List<TransactionDTO> filteredTransactions = new ArrayList<>();
        for (TransactionDTO transaction : transactions) {
            if ((transaction.getDate().isAfter(dateFrom) && transaction.getDate().isBefore(dateTo))
                || transaction.getDate().equals(dateFrom) || transaction.getDate().equals(dateTo)) {
                if (transaction.getCategoryId() == categoryId && transaction.getUserId().equals(userId)) {
                    filteredTransactions.add(transaction);
                }
            }
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCashback = BigDecimal.ZERO;
        BigDecimal potentialProfit = BigDecimal.ZERO;

        //TODO get current cashback from each bank
        HashMap<UUID, Double> bankCashback = new HashMap<>();
        bankCashback.put(UUID.fromString("Bank 1"), 0.1);
        bankCashback.put(UUID.fromString("Bank 2"), 0.2);
        bankCashback.put(UUID.fromString("Bank 3"), 0.15);

        //TODO get best cashback ratio
        UUID optimalBankId = bankCashback.keySet().iterator().next();
        Double optimalCashback = null;
        for (UUID bankUUID: bankCashback.keySet()) {
            if (optimalCashback == null) {
                optimalBankId = bankUUID;
                optimalCashback = bankCashback.get(bankUUID);
            } else if (optimalCashback < bankCashback.get(bankUUID)) {
                optimalBankId = bankUUID;
                optimalCashback = bankCashback.get(bankUUID);
            }
        }

        for (TransactionDTO transaction : filteredTransactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
            totalCashback = totalCashback.add(transaction.getAmount().multiply(BigDecimal.valueOf(bankCashback.get(transaction.getBankId()))));
            potentialProfit = potentialProfit.add(transaction.getAmount().multiply(BigDecimal.valueOf(optimalCashback)));
        }

        CategoryReportDTO categoryReportDTO = new CategoryReportDTO(
            categoryId,
            dateFrom,
            dateTo,
            optimalBankId,
            totalAmount,
            totalCashback,
            potentialProfit
        );

        return categoryReportDTO;
    }
}
