package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PiggyBankDTO {
    private UUID userId;
    private BigDecimal amount;
    private String goal;
}
