package innohackatons.api;

import innohackatons.api.model.DeletePiggyBankRequest;
import innohackatons.api.model.DeletePiggyBankResponse;
import innohackatons.api.model.GetAllPiggyBanksByUserResponse;
import innohackatons.api.model.GetPiggyBankInfoResponse;
import innohackatons.api.model.InternalPiggyBankResponse;
import innohackatons.api.model.PostInternalPiggyBankRequest;
import innohackatons.api.model.PostNewPiggyBankResponse;
import innohackatons.service.PiggyBankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PiggyBankController implements PiggyBankAPI {
    private final PiggyBankService piggyBankService;

    @Override
    public ResponseEntity<GetAllPiggyBanksByUserResponse> getAllPiggyBanksByUser(long userId) {
        return piggyBankService.getAllPiggyBanksByUser(userId);
    }

    @Override
    public ResponseEntity<GetPiggyBankInfoResponse> getPiggyBank(long userId, String goal) {
        return piggyBankService.getPiggyBank(userId, goal);
    }

    @Override
    public ResponseEntity<InternalPiggyBankResponse> internalAddTransactionProcess(
        long userId,
        PostInternalPiggyBankRequest request
    ) {
        return piggyBankService.internalAddTransactionProcess(userId, request);
    }

    @Override
    public ResponseEntity<InternalPiggyBankResponse> internalRetrieveTransactionProcess(
        long userId,
        PostInternalPiggyBankRequest request
    ) {
        return piggyBankService.internalRetrieveTransactionProcess(userId, request);
    }

    @Override
    public ResponseEntity<PostNewPiggyBankResponse> createPiggyBank(long userId, String goal) {
        return piggyBankService.createPiggyBank(userId, goal);
    }

    @Override
    public ResponseEntity<DeletePiggyBankResponse> deletePiggyBank(long userId, DeletePiggyBankRequest request) {
        return piggyBankService.deletePiggyBank(userId, request);
    }
}
