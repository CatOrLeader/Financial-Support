package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransactionDTO {
    private Long userId;
    private Long bankId;
    private Long categoryId;
    private BigDecimal amount;
    private OffsetDateTime date;
}
