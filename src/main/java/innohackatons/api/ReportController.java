package innohackatons.api;

import innohackatons.api.model.GetCategoryReportRequest;
import innohackatons.api.model.GetCategoryReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ReportController implements ReportAPI {
    @Override
    public ResponseEntity<GetCategoryReportResponse> getCategoryReport(long userId, GetCategoryReportRequest request) {
        return null;
    }
}
