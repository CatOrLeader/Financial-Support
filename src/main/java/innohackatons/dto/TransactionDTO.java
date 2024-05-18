package innohackatons.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class TransactionDTO {
    private Long userId;
    private Long bankId;
    private Long categoryId;
    private BigDecimal amount;
    private OffsetDateTime date;
}
