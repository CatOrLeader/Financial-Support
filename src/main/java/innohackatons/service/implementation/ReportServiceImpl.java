package innohackatons.service.implementation;

import innohackatons.api.model.GetCategoryReportRequest;
import innohackatons.api.model.GetCategoryReportResponse;
import innohackatons.entity.Bank;
import innohackatons.entity.Cashback;
import innohackatons.entity.Deposit;
import innohackatons.entity.Transaction;
import innohackatons.repository.CashbackRepository;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.TransactionRepository;
import innohackatons.service.ReportService;
import innohackatons.service.dto.CategoryReportDTO;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings("MagicNumber")
public class ReportServiceImpl implements ReportService {
    private final TransactionRepository transactionRepository;
    private final CashbackRepository cashbackRepository;
    private final DepositRepository depositRepository;

    @Override
    public ResponseEntity<GetCategoryReportResponse> generateCategoryReport(
        long userId, GetCategoryReportRequest request) {
        List<Transaction> filteredTransactions = transactionRepository
            .findTransactionsByUserIdAndCategoryIdAndDateRange(
            userId, request.categoryId(), request.dateFrom(), request.dateTo());

        HashSet<Deposit> deposits = new HashSet<>();

        for (Transaction transaction : filteredTransactions) {
            deposits.addAll(depositRepository.findByBankAndUser(transaction.getBank(), transaction.getUser()));
        }

        HashSet<Cashback> cashbacks = new HashSet<>();

        for (Deposit deposit : deposits) {
            cashbacks.addAll(cashbackRepository.findByBank(deposit.getBank()));
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCashback = BigDecimal.ZERO;
        BigDecimal potentialProfit = BigDecimal.ZERO;

        Bank optimalBank = cashbacks.iterator().next().getBank();
        BigDecimal optimalCashback = cashbacks.iterator().next().getRatio();
        for (Cashback cashback : cashbacks) {
            if (optimalCashback.compareTo(cashback.getRatio()) < 0) {
                optimalBank = cashback.getBank();
                optimalCashback = cashback.getRatio();
            }
        }

        for (Transaction transaction : filteredTransactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
            totalCashback = totalCashback.add(transaction.getAmount()
                .multiply(cashbackRepository
                    .findByBankAndCategory(transaction.getBank(), transaction.getCategory()).getRatio()));
            potentialProfit =
                potentialProfit.add(transaction.getAmount().multiply(optimalCashback));
        }

        CategoryReportDTO categoryReportDTO = new CategoryReportDTO(
            request.categoryId(),
            request.dateFrom(),
            request.dateTo(),
            optimalBank.getId(),
            totalAmount,
            totalCashback,
            potentialProfit
        );

        return ResponseEntity.ok(
            new GetCategoryReportResponse(categoryReportDTO)
        );
    }
}
