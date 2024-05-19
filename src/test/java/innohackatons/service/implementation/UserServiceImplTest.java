package innohackatons.service.implementation;

import innohackatons.IntegrationEnvironment;
import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.UserService;
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
class UserServiceImplTest extends IntegrationEnvironment {
    private static long ID;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepositRepository depositRepository;

    @Test
    @Order(1)
    void getUser_whenNothingRegistered_thenZeroFound() {
        depositRepository.deleteAll();
        userRepository.deleteAll();

        assertThat(userService.getAllUsers().getBody().userInfoResponseList()).isEmpty();
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> userService.getUser(1));
    }

    @Test
    @Order(2)
    void registerUser_whenNoOneRegistered_thenOneFound() {
        ID = userService.registerUser("Artur")
            .getBody().userId();

        assertThat(userService.getAllUsers().getBody().userInfoResponseList()).containsExactly(new GetUserInfoResponse(
            "Artur"));
        assertThat(userService.getUser(ID).getBody().name()).isEqualTo("Artur");
    }

    @Test
    @Order(3)
    void registerUser_whenAlreadyRegistered_thenConflict() {
        assertThatExceptionOfType(ConflictException.class)
            .isThrownBy(() -> userService.registerUser("Artur"));
    }

    @Test
    @Order(4)
    void deleteUser_whenOneRegistered_thenNoOne() {
        userService.deleteUser(ID);

        assertThat(userService.getAllUsers().getBody().userInfoResponseList()).isEmpty();
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> userService.getUser(ID));
    }

    @Test
    @Order(4)
    void deleteUser_whenNoOneRegistered_thenNotFound() {
        assertThatExceptionOfType(NotFoundEntityException.class)
            .isThrownBy(() -> userService.deleteUser(ID));
    }
}
