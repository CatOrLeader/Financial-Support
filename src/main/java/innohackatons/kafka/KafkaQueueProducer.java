package innohackatons.kafka;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.api.model.TransactionResponse;
import innohackatons.configuration.ApplicationConfiguration;
import innohackatons.service.TransactionService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaQueueProducer implements TransactionService {
    private final ApplicationConfiguration config;
    private final KafkaTemplate<Long, PostTransactionRequest> kafkaTemplate;

    @Override
    public ResponseEntity<TransactionResponse> processTransaction(PostTransactionRequest request) {

        kafkaTemplate.send(config.kafkaConfigInfo().updatesTopic().name(), request.userId(), request);

        TransactionResponse response = new TransactionResponse(LocalDateTime.now());

        return ResponseEntity.ok(response);

    }
}
