package innohackatons.api;

import innohackatons.api.model.DeletePiggyBankRequest;
import innohackatons.api.model.DeletePiggyBankResponse;
import innohackatons.api.model.GetAllPiggyBanksByUserResponse;
import innohackatons.api.model.GetPiggyBankInfoResponse;
import innohackatons.api.model.InternalPiggyBankResponse;
import innohackatons.api.model.PostInternalPiggyBankRequest;
import innohackatons.service.PiggyBankService;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PiggyBankController.class)
@DirtiesContext
class PiggyBankControllerTest {
    private static final String COMMON_AUTHORIZATION_HEADER_NAME = "Token";
    private static final String COMMON_HEADER_VALUE = "Value";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PiggyBankService piggyBankService;

    @Test
    void testMapping_whenAddMoneyToPiggyBank() {
        webClient.post()
            .uri(uri -> uri.path("/piggy-bank/internal/add").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new PostInternalPiggyBankRequest("Food", 1.0, 1))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void givenAddMoneyToPiggyBankRequest_whenAllCorrect_thenCorrectResponse() {
        final long userId = 1;
        PostInternalPiggyBankRequest request = new PostInternalPiggyBankRequest("Food", 1.0, 1);
        InternalPiggyBankResponse response = new InternalPiggyBankResponse(2.0);
        Mockito.when(piggyBankService.internalAddTransactionProcess(userId, request))
            .thenReturn(ResponseEntity.ok(response));

        webClient.post()
            .uri(uri -> uri.path("/piggy-bank/internal/add").queryParam("userId", userId).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody(InternalPiggyBankResponse.class)
            .value(InternalPiggyBankResponse::totalMoney, Matchers.equalTo(2.0));
    }

    @Test
    void testMapping_whenRetrieveMoneyFromPiggyBank() {
        webClient.post()
            .uri(uri -> uri.path("/piggy-bank/internal/retrieve").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new PostInternalPiggyBankRequest("Food", 1.0, 1))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void givenRetrieveMoneyFromPiggyBankRequest_whenAllCorrect_thenCorrectResponse() {
        final long userId = 1;
        PostInternalPiggyBankRequest request = new PostInternalPiggyBankRequest("Food", 1.0, 1);
        InternalPiggyBankResponse response = new InternalPiggyBankResponse(0.0);
        Mockito.when(piggyBankService.internalRetrieveTransactionProcess(userId, request))
            .thenReturn(ResponseEntity.ok(response));

        webClient.post()
            .uri(uri -> uri.path("/piggy-bank/internal/retrieve").queryParam("userId", userId).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody(InternalPiggyBankResponse.class)
            .value(InternalPiggyBankResponse::totalMoney, Matchers.equalTo(0.0));
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
    void givenGetPiggyBankByUserAndGoalRequest_whenAllCorrect_thenCorrectResponse() {
        final long userId = 1;
        final String goal = "Food";
        GetPiggyBankInfoResponse response = new GetPiggyBankInfoResponse(goal, 100.0);
        Mockito.when(piggyBankService.getPiggyBank(userId, goal)).thenReturn(ResponseEntity.ok(response));

        webClient.get()
            .uri(uri -> uri.path("/piggy-bank")
                .queryParam("userId", userId)
                .queryParam("goal", goal).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GetPiggyBankInfoResponse.class)
            .value(GetPiggyBankInfoResponse::goal, Matchers.equalTo(goal))
            .value(GetPiggyBankInfoResponse::amount, Matchers.equalTo(100.0));
    }

    @Test
    void testMapping_whenGetAllPiggyBanksByUser() {
        webClient.get()
            .uri(uri -> uri.path("/piggy-bank/all").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void givenGetAllPiggyBanksByUserRequest_whenAllCorrect_thenCorrectResponse() {
        final long userId = 1;
        List<GetPiggyBankInfoResponse> piggyBankInfoList = new ArrayList<>();
        piggyBankInfoList.add(new GetPiggyBankInfoResponse("Food", 100.0));
        GetAllPiggyBanksByUserResponse response = new GetAllPiggyBanksByUserResponse(piggyBankInfoList, 1);
        Mockito.when(piggyBankService.getAllPiggyBanksByUser(userId)).thenReturn(ResponseEntity.ok(response));

        webClient.get()
            .uri(uri -> uri.path("/piggy-bank/all").queryParam("userId", userId).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GetAllPiggyBanksByUserResponse.class)
            .value(GetAllPiggyBanksByUserResponse::size, Matchers.equalTo(1))
            .value(res -> res.bankInfoResponseList().size(), Matchers.equalTo(1))
            .value(res -> res.bankInfoResponseList().get(0).goal(), Matchers.equalTo("Food"))
            .value(res -> res.bankInfoResponseList().get(0).amount(), Matchers.equalTo(100.0));
    }

    @Test
    void testMapping_whenDeletePiggyBank() {
        webClient.method(HttpMethod.DELETE)
            .uri(uri -> uri.path("/piggy-bank").queryParam("userId", 1).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(new DeletePiggyBankRequest("Food", 1))
            .exchange()
            .expectStatus()
            .value(Matchers.not(404));
    }

    @Test
    void givenDeletePiggyBankRequest_whenAllCorrect_thenCorrectResponse() {
        final long userId = 1;
        DeletePiggyBankRequest request = new DeletePiggyBankRequest("Food", 1);
        DeletePiggyBankResponse response = new DeletePiggyBankResponse(100.0);
        Mockito.when(piggyBankService.deletePiggyBank(userId, request))
            .thenReturn(ResponseEntity.ok(response));

        webClient.method(HttpMethod.DELETE)
            .uri(uri -> uri.path("/piggy-bank").queryParam("userId", userId).build())
            .header(COMMON_AUTHORIZATION_HEADER_NAME, COMMON_HEADER_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody(DeletePiggyBankResponse.class)
            .value(DeletePiggyBankResponse::moneyTransferred, Matchers.equalTo(100.0));
    }
}
