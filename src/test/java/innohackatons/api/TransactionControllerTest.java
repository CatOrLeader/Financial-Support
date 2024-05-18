package innohackatons.api;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.service.TransactionService;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransactionController.class)
@DirtiesContext
public class TransactionControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Token";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private TransactionService service;

    @Test
    void testMapping() {
        webClient.post()
            .uri("/transaction")
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new PostTransactionRequest(
                1, 1, 1, 1.0, LocalDateTime.now()
            ))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }
}