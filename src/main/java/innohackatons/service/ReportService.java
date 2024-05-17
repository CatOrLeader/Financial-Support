package innohackatons.service;

import innohackatons.dto.CategoryDTO;
import innohackatons.dto.CategoryReportDTO;
import innohackatons.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface ReportService {
    CategoryReportDTO generateCategoryReport(Long categoryId, Long userId, OffsetDateTime dateFrom, OffsetDateTime dateTo, List<TransactionDTO> transactions);
}
