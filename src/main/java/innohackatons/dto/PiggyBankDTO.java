package innohackatons.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PiggyBankDTO {
    private Long userId;
    private BigDecimal amount;
    private String goal;
}
