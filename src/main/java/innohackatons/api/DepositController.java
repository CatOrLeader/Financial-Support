package innohackatons.api;

import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DepositController implements DepositAPI {
    @Override
    public ResponseEntity<PostNewDepositResponse> createNewDeposit(long userId, long bankId) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<GetDepositInfoResponse> getDeposit(long depositId) {
        return null;
    }

    @Override
    public ResponseEntity<GetAllDepositsInfoResponse> getAllDepositsByUserId(long userId) {
        return null;
    }
}
