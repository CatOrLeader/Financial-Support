package innohackatons.api;

import innohackatons.api.model.DeletePiggyBankRequest;
import innohackatons.api.model.PostInternalPiggyBankRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PiggyBankController.class)
@DirtiesContext
public class PiggyBankControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @Test
    void testMapping_whenAddMoneyToPiggyBank() {
        webClient.post()
            .uri(uri -> uri.path("/piggy-bank/internal/add").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new PostInternalPiggyBankRequest(
                "Food", 1.0, 1
            ))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenRetrieveMoneyFromPiggyBank() {
        webClient.post()
            .uri(uri -> uri.path("/piggy-bank/internal/retrieve").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new PostInternalPiggyBankRequest(
                "Food", 1.0, 1
            ))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenGetPiggyBankByUserAndGoal() {
        webClient.get()
            .uri(uri -> uri.path("/piggy-bank")
                .queryParam("userId", 1)
                .queryParam("goal", "Food").build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenGetAllPiggyBanksByUser() {
        webClient.get()
            .uri(uri -> uri.path("/piggy-bank/all")
                .queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenDeletePiggyBank() {
        webClient.method(HttpMethod.DELETE)
            .uri(uri -> uri.path("/piggy-bank").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new DeletePiggyBankRequest(
                "Food", 1
            ))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }
}
