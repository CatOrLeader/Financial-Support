package innohackatons.service;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.api.model.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

    @Transactional
    ResponseEntity<TransactionResponse> processTransaction(PostTransactionRequest transaction);
}
