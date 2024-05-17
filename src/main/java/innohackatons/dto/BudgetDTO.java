package innohackatons.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class BudgetDTO {
    private UUID userId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;
    private UUID categoryId;
    private UUID bankId;
    private BigDecimal threshold;
}
