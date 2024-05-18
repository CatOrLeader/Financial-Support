package innohackatons.service;

import innohackatons.entity.Transaction;
import innohackatons.service.dto.CategoryReportDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    CategoryReportDTO generateCategoryReport(
        Long categoryId,
        Long userId,
        LocalDateTime dateFrom,
        LocalDateTime dateTo,
        List<Transaction> transactions
    );
}
