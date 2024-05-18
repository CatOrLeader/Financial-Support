package innohackatons.api;

import innohackatons.api.model.GetAllUsersInfoResponse;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.api.model.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserAPI {
    @Override
    public ResponseEntity<UserRegisterResponse> registerUser(String name) {
        return null;
    }

    @Override
    public ResponseEntity<GetUserInfoResponse> getUser(long userId) {
        return null;
    }

    @Override
    public ResponseEntity<GetAllUsersInfoResponse> getUser() {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteUser(long name) {
        return null;
    }
}
