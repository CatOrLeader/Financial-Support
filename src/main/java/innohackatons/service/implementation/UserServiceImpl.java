package innohackatons.service.implementation;

import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.GetAllUsersInfoResponse;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.api.model.UserRegisterResponse;
import innohackatons.entity.User;
import innohackatons.repository.UserRepository;
import innohackatons.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_CONFLICT = "User is already exists";
    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<UserRegisterResponse> registerUser(String name) {
        var maybeUser = userRepository.findUserByName(name);
        if (maybeUser.isPresent()) {
            throw new ConflictException(USER_CONFLICT);
        }

        var user = userRepository.save(new User().setName(name));

        return ResponseEntity.ok(
            new UserRegisterResponse(user.getId())
        );
    }

    @Override
    public ResponseEntity<GetUserInfoResponse> getUser(long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));

        return ResponseEntity.ok(
            new GetUserInfoResponse(user.getName())
        );
    }

    @Override
    public ResponseEntity<GetAllUsersInfoResponse> getAllUsers() {
        var users = userRepository.findAll();

        return ResponseEntity.ok(
            new GetAllUsersInfoResponse(
                users.stream()
                    .map(user -> new GetUserInfoResponse(user.getName()))
                    .toList(),
                users.size()
            )
        );
    }

    @Override
    public ResponseEntity<Object> deleteUser(long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));

        userRepository.delete(user);
        return ResponseEntity.ok(Void.class);
    }
}
