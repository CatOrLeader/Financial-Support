package innohackatons.service;

import innohackatons.api.model.GetAllUsersInfoResponse;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.api.model.UserRegisterResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserRegisterResponse> registerUser(@NotBlank String name);

    ResponseEntity<GetUserInfoResponse> getUser(@Min(0) long userId);

    ResponseEntity<GetAllUsersInfoResponse> getAllUsers();

    ResponseEntity<Object> deleteUser(@Min(0) long userId);
}
