package innohackatons.api;

import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import innohackatons.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DepositController implements DepositAPI {
    private final DepositService depositService;

    @Override
    public ResponseEntity<PostNewDepositResponse> createNewDeposit(long userId, long bankId) {
        return depositService.createNewDeposit(userId, bankId);
    }

    @Override
    public ResponseEntity<GetDepositInfoResponse> getDeposit(long depositId) {
        return depositService.getDeposit(depositId);
    }

    @Override
    public ResponseEntity<GetAllDepositsInfoResponse> getAllDepositsByUserId(long userId) {
        return depositService.getAllDepositsByUserId(userId);
    }
}
