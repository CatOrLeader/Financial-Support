package innohackatons.api;

import innohackatons.api.model.GetAllUsersInfoResponse;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.api.model.UserRegisterResponse;
import innohackatons.service.UserService;
import java.util.ArrayList;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@DirtiesContext
class UserControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Token";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserService userService;

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
    void givenGetSingleUserRequest_whenAllCorrect_thenCorrectResponse() {
        final long id = 1;
        Mockito.when(userService.getUser(id)).thenReturn(
            ResponseEntity.ok(
                new GetUserInfoResponse("NAME")
            )
        );

        webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/user").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GetUserInfoResponse.class).value(GetUserInfoResponse::name, Matchers.equalTo("NAME"));
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
    void givenGetAllUsersRequest_whenAllCorrect_thenCorrectResponse() {
        var response = new GetAllUsersInfoResponse(new ArrayList<>(), 0);
        Mockito.when(userService.getAllUsers())
            .thenReturn(ResponseEntity.ok(response));

        webClient.get()
            .uri("/user/all")
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GetAllUsersInfoResponse.class).isEqualTo(response);
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
    void givenRegisterUserRequest_whenAllCorrect_thenCorrectResponse() {
        final String name = "NAME";
        Mockito.when(userService.registerUser(name)).thenReturn(
            ResponseEntity.ok(
                new UserRegisterResponse(1)
            )
        );

        webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/user/register").queryParam("name", name).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(UserRegisterResponse.class).value(UserRegisterResponse::userId, Matchers.equalTo(1L));
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

    @Test
    void givenDeleteUserRequest_whenAllCorrect_thenCorrectResponse() {
        Mockito.when(userService.deleteUser(1))
            .thenReturn(ResponseEntity.ok(null));

        webClient.delete()
            .uri(uriBuilder -> uriBuilder.path("/user").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty();
    }
}
