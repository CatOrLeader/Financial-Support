package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class TransactionDTO {
    private UUID userId;
    private UUID bankId;
    private UUID categoryId;
    private BigDecimal amount;
    private OffsetDateTime date;
}
