package innohackatons.kafka;

import innohackatons.api.model.PostTransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionConsumer {

    @KafkaListener(groupId = "transaction_group",
                   topics = "${app.kafka-config-info.updates-topic.name}",
                   containerFactory = "transactionConcurrentKafkaListenerContainerFactory")
    public void consume(@Payload PostTransactionRequest message) {
        log.info("Consumed message: {}", message);

        // message processing logic (business logic)

        // Like save transaction to database or perform other actions
    }
}

