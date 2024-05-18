package innohackatons.api;

import innohackatons.api.model.GetCategoryReportRequest;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReportController.class)
@DirtiesContext
public class ReportControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @Test
    void testMapping() {
        webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/report/category").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new GetCategoryReportRequest(
                1, LocalDateTime.MIN, LocalDateTime.MAX
            ))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }
}
