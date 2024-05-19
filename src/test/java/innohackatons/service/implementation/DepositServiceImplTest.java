package innohackatons.service.implementation;

import innohackatons.IntegrationEnvironment;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import innohackatons.entity.Bank;
import innohackatons.entity.User;
import innohackatons.repository.BankRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.DepositService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepositServiceImplTest extends IntegrationEnvironment {

    private static long userId;
    private static long bankId;
    private static long depositId;

    @Autowired
    private DepositService depositService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Test
    @Order(1)
    void createDeposit_whenUserAndBankExist_thenDepositCreated() {
        User user = new User();
        user.setName("TestUser");
        user = userRepository.save(user);
        userId = user.getId();

        Bank bank = bankRepository.findById(1L).get();
        bankId = bank.getId();

        PostNewDepositResponse response = depositService.createNewDeposit(userId, bankId).getBody();
        depositId = response.id();

        assertThat(response).isNotNull();
        assertThat(depositId).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void getDeposit_whenDepositExists_thenReturnDepositInfo() {
        GetDepositInfoResponse response = depositService.getDeposit(depositId).getBody();

        assertThat(response).isNotNull();
        assertThat(response.bankId()).isEqualTo(bankId);
        assertThat(response.amount()).isEqualTo(0.0);
    }

    @Test
    @Order(3)
    void getAllDepositsByUserId_whenDepositsExist_thenReturnDepositsInfo() {
        GetAllDepositsInfoResponse response = depositService.getAllDepositsByUserId(userId).getBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.depositInfoResponses().get(0).bankId()).isEqualTo(bankId);
        assertThat(response.depositInfoResponses().get(0).amount()).isEqualTo(0.0);
    }

    @Test
    @Order(4)
    void createDeposit_whenUserNotExist_thenThrowNotFoundEntityException() {
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> depositService.createNewDeposit(999L, bankId));
    }

    @Test
    @Order(5)
    void createDeposit_whenBankNotExist_thenThrowNotFoundEntityException() {
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> depositService.createNewDeposit(userId, 999L));
    }

    @Test
    @Order(6)
    void getDeposit_whenDepositNotExist_thenThrowNotFoundEntityException() {
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> depositService.getDeposit(999L));
    }

    @Test
    @Order(7)
    void getAllDepositsByUserId_whenUserNotExist_thenThrowNotFoundEntityException() {
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> depositService.getAllDepositsByUserId(999L));
    }
}
