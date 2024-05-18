package innohackatons.service;

import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface DepositService {
    @Transactional
    ResponseEntity<PostNewDepositResponse> createNewDeposit(long userId, long bankId);

    @Transactional
    ResponseEntity<GetDepositInfoResponse> getDeposit(long depositId);

    @Transactional
    ResponseEntity<GetAllDepositsInfoResponse> getAllDepositsByUserId(long userId);
}
