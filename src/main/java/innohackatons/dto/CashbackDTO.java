package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CashbackDTO {
    private UUID bankId;
    private UUID categoryId;
    private BigDecimal ratio;
}
