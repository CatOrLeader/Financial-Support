package innohackatons.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = DepositController.class)
@DirtiesContext
public class DepositControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @Test
    void testMapping_whenGetDepositById() {
        webClient.get()
            .uri(uri -> uri.path("/deposit").queryParam("depositId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenPostNewDeposit() {
        webClient.post()
            .uri(uri -> uri.path("/deposit")
                .queryParam("userId", 1)
                .queryParam("bankId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenGetAllDepositByUserId() {
        webClient.get()
            .uri(uri -> uri.path("/deposit/all").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }
}
