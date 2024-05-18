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
@WebFluxTest(controllers = UserController.class)
@DirtiesContext
public class UserControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Token";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @Test
    void testMapping_whenGetSingleUser() {
        webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/user").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenGetAllUsers() {
        webClient.get()
            .uri("/user/all")
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenRegister() {
        webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/user/register").queryParam("name", "new_name").build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void testMapping_whenDeleteUser() {
        webClient.delete()
            .uri(uriBuilder -> uriBuilder.path("/user").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }
}
