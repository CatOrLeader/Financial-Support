package innohackatons.service;

import innohackatons.dto.CategoryReportDTO;
import innohackatons.dto.TransactionDTO;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    CategoryReportDTO generateCategoryReport(
        Long categoryId,
        Long userId,
        OffsetDateTime dateFrom,
        OffsetDateTime dateTo,
        List<TransactionDTO> transactions
    );
}
