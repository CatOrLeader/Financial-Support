package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PiggyBankDTO {
    private Long userId;
    private BigDecimal amount;
    private String goal;
}
