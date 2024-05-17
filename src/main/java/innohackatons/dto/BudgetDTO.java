package innohackatons.dto;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class BudgetDTO {
    private Long userId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;
    private Long categoryId;
    private Long bankId;
    private Double threshold;
}
