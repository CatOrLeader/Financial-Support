package innohackatons.service;

import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface DepositService {
    @Transactional
    ResponseEntity<PostNewDepositResponse> createNewDeposit(@Min(0) long userId, @Min(0) long bankId);

    @Transactional
    ResponseEntity<GetDepositInfoResponse> getDeposit(@Min(0) long depositId);

    @Transactional
    ResponseEntity<GetAllDepositsInfoResponse> getAllDepositsByUserId(@Min(0) long userId);
}
