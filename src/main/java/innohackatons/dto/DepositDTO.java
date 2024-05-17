package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DepositDTO {
    private UUID id;
    private UUID userId;
    private UUID bankId;
    private BigDecimal amount;
}
