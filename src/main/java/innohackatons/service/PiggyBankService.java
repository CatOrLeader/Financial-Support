package innohackatons.service;

import innohackatons.api.model.DeletePiggyBankRequest;
import innohackatons.api.model.DeletePiggyBankResponse;
import innohackatons.api.model.GetAllPiggyBanksByUserResponse;
import innohackatons.api.model.GetPiggyBankInfoResponse;
import innohackatons.api.model.InternalPiggyBankResponse;
import innohackatons.api.model.PostInternalPiggyBankRequest;
import innohackatons.api.model.PostNewPiggyBankResponse;
import org.springframework.http.ResponseEntity;

public interface PiggyBankService {
    ResponseEntity<GetAllPiggyBanksByUserResponse> getAllPiggyBanksByUser(long userId);

    ResponseEntity<GetPiggyBankInfoResponse> getPiggyBank(long userId, String goal);

    ResponseEntity<InternalPiggyBankResponse> internalAddTransactionProcess(
        long userId,
        PostInternalPiggyBankRequest request
    );

    ResponseEntity<InternalPiggyBankResponse> internalRetrieveTransactionProcess(
        long userId,
        PostInternalPiggyBankRequest request
    );

    ResponseEntity<PostNewPiggyBankResponse> createPiggyBank(long userId, String goal);

    ResponseEntity<DeletePiggyBankResponse> deletePiggyBank(long userId, DeletePiggyBankRequest request);
}
