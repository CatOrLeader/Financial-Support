package innohackatons.service.implementation;

import innohackatons.IntegrationEnvironment;
import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.DeletePiggyBankRequest;
import innohackatons.api.model.DeletePiggyBankResponse;
import innohackatons.api.model.GetAllPiggyBanksByUserResponse;
import innohackatons.api.model.GetPiggyBankInfoResponse;
import innohackatons.api.model.InternalPiggyBankResponse;
import innohackatons.api.model.PostInternalPiggyBankRequest;
import innohackatons.api.model.PostNewPiggyBankResponse;
import innohackatons.entity.Deposit;
import innohackatons.entity.User;
import innohackatons.repository.BankRepository;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.PiggyBankRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.PiggyBankService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PiggyBankServiceImplTest extends IntegrationEnvironment {

    private static long userId;
    private static long depositId;
    private static long piggyBankId;

    @Autowired
    private PiggyBankService piggyBankService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private PiggyBankRepository piggyBankRepository;

    @Test
    @Order(1)
    void createPiggyBank_whenUserExists_thenPiggyBankCreated() {
        User user = new User();
        user.setName("TestUser");
        user = userRepository.save(user);
        userId = user.getId();
        PostNewPiggyBankResponse response = piggyBankService.createPiggyBank(userId, "NewGoal").getBody();
        piggyBankId = response.piggyBankId();

        assertThat(response).isNotNull();
        assertThat(piggyBankId).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void getPiggyBank_whenPiggyBankExists_thenReturnPiggyBankInfo() {
        GetPiggyBankInfoResponse response = piggyBankService.getPiggyBank(userId, "NewGoal").getBody();

        assertThat(response).isNotNull();
        assertThat(response.goal()).isEqualTo("NewGoal");
        assertThat(response.amount()).isEqualTo(0.0);
    }

    @Test
    @Order(3)
    void createPiggyBank_whenPiggyBankAlreadyExists_thenThrowConflictException() {
        assertThatExceptionOfType(ConflictException.class)
            .isThrownBy(() -> piggyBankService.createPiggyBank(userId, "NewGoal"));
    }


    @Test
    @Order(4)
    void getAllPiggyBanksByUser_whenPiggyBanksExist_thenReturnPiggyBanksInfo() {
        GetAllPiggyBanksByUserResponse response = piggyBankService.getAllPiggyBanksByUser(userId).getBody();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.bankInfoResponseList().get(0).goal()).isEqualTo("NewGoal");
    }

    @Test
    @Order(5)
    void internalAddTransactionProcess_whenValid_thenUpdateAmounts() {
        Deposit deposit = new Deposit();
        deposit.setUser(userRepository.findById(userId).get()).setBank(bankRepository.findById(1L).get());
        deposit.setAmount(BigDecimal.valueOf(1000));
        deposit = depositRepository.save(deposit);
        depositId = deposit.getId();

        PostInternalPiggyBankRequest request = new PostInternalPiggyBankRequest("NewGoal", 1000, depositId);

        InternalPiggyBankResponse response = piggyBankService.internalAddTransactionProcess(userId, request).getBody();

        assertThat(response).isNotNull();
        assertThat(response.totalMoney()).isEqualTo(1000);
        assertThat(depositRepository.findById(depositId).get().getAmount().doubleValue()).isEqualTo(0);
    }

    @Test
    @Order(6)
    void internalRetrieveTransactionProcess_whenValid_thenUpdateAmounts() {
        PostInternalPiggyBankRequest request = new PostInternalPiggyBankRequest("NewGoal", 10, depositId);
        InternalPiggyBankResponse response = piggyBankService.internalRetrieveTransactionProcess(userId, request).getBody();

        assertThat(response).isNotNull();
        assertThat(response.totalMoney()).isEqualTo(990);
        assertThat(depositRepository.findById(depositId).get().getAmount().doubleValue()).isEqualTo(10);
    }

    @Test
    @Order(7)
    void deletePiggyBank_whenPiggyBankExists_thenDeleteAndTransferMoney() {
        DeletePiggyBankRequest request = new DeletePiggyBankRequest("NewGoal", depositId);

        DeletePiggyBankResponse response = piggyBankService.deletePiggyBank(userId, request).getBody();

        assertThat(response).isNotNull();
        assertThat(response.moneyTransferred()).isEqualTo(990);
        assertThat(depositRepository.findById(depositId).get().getAmount().doubleValue()).isEqualTo(10);
        assertThat(piggyBankRepository.findById(piggyBankId)).isEmpty();
    }

    @Test
    @Order(8)
    void createPiggyBank_whenUserNotExist_thenThrowNotFoundEntityException() {
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> piggyBankService.createPiggyBank(999L, "NonExistentGoal"));
    }


    @Test
    @Order(9)
    void internalAddTransactionProcess_whenDepositNotExist_thenThrowNotFoundEntityException() {
        PostInternalPiggyBankRequest request = new PostInternalPiggyBankRequest("NewGoal", 100, 99L);

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> piggyBankService.internalAddTransactionProcess(userId, request));
    }

    @Test
    @Order(10)
    void internalRetrieveTransactionProcess_whenPiggyBankNotExist_thenThrowNotFoundEntityException() {
        PostInternalPiggyBankRequest request = new PostInternalPiggyBankRequest("NonExistingGoal", 100, 99L);

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> piggyBankService.internalRetrieveTransactionProcess(userId, request));
    }

    @Test
    @Order(11)
    void deletePiggyBank_whenPiggyBankNotExist_thenThrowNotFoundEntityException() {
        DeletePiggyBankRequest request = new DeletePiggyBankRequest( "NonExistentGoal", 99L);

        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> piggyBankService.deletePiggyBank(userId, request));
    }
}
