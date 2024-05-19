package innohackatons.kafka.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaTransactionMessage {
    private Long transactionId;
    private Long userId;
    private Long bankId;
    private Long categoryId;
    private BigDecimal amount;
    private LocalDateTime date;
}

