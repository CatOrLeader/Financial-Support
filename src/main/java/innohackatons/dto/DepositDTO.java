package innohackatons.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DepositDTO {
    private Long id;
    private Long userId;
    private Long bankId;
    private BigDecimal amount;
}
