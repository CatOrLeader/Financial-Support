package innohackatons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReportDTO {
    private UUID categoryId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;
    private UUID recommendedBankId;
    private BigDecimal amountSpent;
    private BigDecimal actualProfit;
    private BigDecimal potentialProfit;
}
