package innohackatons.api;

import innohackatons.api.model.GetAllUsersInfoResponse;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.api.model.UserRegisterResponse;
import innohackatons.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    public ResponseEntity<UserRegisterResponse> registerUser(String name) {
        return userService.registerUser(name);
    }

    @Override
    public ResponseEntity<GetUserInfoResponse> getUser(long userId) {
        return userService.getUser(userId);
    }

    @Override
    public ResponseEntity<GetAllUsersInfoResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public ResponseEntity<Object> deleteUser(long userId) {
        return userService.deleteUser(userId);
    }
}
