package innohackatons.api;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.api.model.TransactionResponse;
import innohackatons.service.TransactionService;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransactionController.class)
@DirtiesContext
class TransactionControllerTest {
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

    @Test
    void assertThatSuccess() {
        PostTransactionRequest request = new PostTransactionRequest(
            1L, 1L, 1L, 100.0, LocalDateTime.now()
        );

        when(service.processTransaction(request))
            .thenReturn(ResponseEntity.ok(new TransactionResponse(LocalDateTime.now())));

        webClient.post()
            .uri("/transaction")
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(request))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.proceeded_date").isNotEmpty();
    }
}
