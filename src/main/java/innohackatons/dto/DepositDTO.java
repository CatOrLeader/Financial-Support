package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DepositDTO {
    private Long id;
    private Long userId;
    private Long bankId;
    private BigDecimal amount;
}
