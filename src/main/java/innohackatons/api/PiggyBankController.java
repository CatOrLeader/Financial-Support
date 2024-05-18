package innohackatons.api;

import innohackatons.api.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PiggyBankController implements PiggyBankAPI {
    @Override
    public ResponseEntity<GetAllPiggyBanksByUserResponse> getAllPiggyBanksByUser(long userId) {
        return null;
    }

    @Override
    public ResponseEntity<GetPiggyBankInfoResponse> getPiggyBank(long userId, String goal) {
        return null;
    }

    @Override
    public ResponseEntity<InternalPiggyBankResponse> internalAddTransactionProcess(
            long userId,
            PostInternalPiggyBankRequest request
    ) {
        return null;
    }

    @Override
    public ResponseEntity<InternalPiggyBankResponse> internalRetrieveTransactionProcess(
            long userId,
            PostInternalPiggyBankRequest request
    ) {
        return null;
    }

    @Override
    public ResponseEntity<DeletePiggyBankRequest> deletePiggyBank(long userId, DeletePiggyBankRequest request) {
        return null;
    }
}
