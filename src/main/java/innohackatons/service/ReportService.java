package innohackatons.service;

import innohackatons.api.model.GetCategoryReportRequest;
import innohackatons.api.model.GetCategoryReportResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    ResponseEntity<GetCategoryReportResponse> generateCategoryReport(long userId, GetCategoryReportRequest request);
}
