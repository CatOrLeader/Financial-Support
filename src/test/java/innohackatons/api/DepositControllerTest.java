package innohackatons.api;

import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import innohackatons.service.DepositService;
import java.util.ArrayList;
import java.util.List;
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
@WebFluxTest(controllers = DepositController.class)
@DirtiesContext
class DepositControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Token";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private DepositService depositService;

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
    void givenGetDepositByIdRequest_whenAllCorrect_thenCorrectResponse() {
        final long id = 1;
        GetDepositInfoResponse response = new GetDepositInfoResponse(1, 100.0);
        Mockito.when(depositService.getDeposit(id)).thenReturn(ResponseEntity.ok(response));

        webClient.get()
            .uri(uri -> uri.path("/deposit").queryParam("depositId", id).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GetDepositInfoResponse.class)
            .value(GetDepositInfoResponse::bankId, Matchers.equalTo(1L))
            .value(GetDepositInfoResponse::amount, Matchers.equalTo(100.0));
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
    void givenPostNewDepositRequest_whenAllCorrect_thenCorrectResponse() {
        final long id = 1;
        PostNewDepositResponse response = new PostNewDepositResponse(id);
        Mockito.when(depositService.createNewDeposit(1, 1)).thenReturn(ResponseEntity.ok(response));

        webClient.post()
            .uri(uri -> uri.path("/deposit")
                .queryParam("userId", 1)
                .queryParam("bankId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(PostNewDepositResponse.class)
            .value(PostNewDepositResponse::id, Matchers.equalTo(id));
    }

    @Test
    void testMapping_whenGetAllDepositsByUserId() {
        webClient.get()
            .uri(uri -> uri.path("/deposit/all").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void givenGetAllDepositsByUserIdRequest_whenAllCorrect_thenCorrectResponse() {
        List<GetDepositInfoResponse> depositInfoResponses = new ArrayList<>();
        depositInfoResponses.add(new GetDepositInfoResponse(1, 100.0));
        GetAllDepositsInfoResponse response = new GetAllDepositsInfoResponse(depositInfoResponses, 1);
        Mockito.when(depositService.getAllDepositsByUserId(1)).thenReturn(ResponseEntity.ok(response));

        webClient.get()
            .uri(uri -> uri.path("/deposit/all").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GetAllDepositsInfoResponse.class)
            .value(GetAllDepositsInfoResponse::size, Matchers.equalTo(1))
            .value(res -> res.depositInfoResponses().size(), Matchers.equalTo(1))
            .value(res -> res.depositInfoResponses().get(0).bankId(), Matchers.equalTo(1L))
            .value(res -> res.depositInfoResponses().get(0).amount(), Matchers.equalTo(100.0));
    }
}
