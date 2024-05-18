package innohackatons.api;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.api.model.TransactionResponse;
import innohackatons.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController implements TransactionAPI {
    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionResponse> processTransaction(PostTransactionRequest transaction) {

        return transactionService.processTransaction(transaction);
    }
}
