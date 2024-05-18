package innohackatons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReportDTO {
    private Long categoryId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;
    private Long recommendedBankId;
    private BigDecimal amountSpent;
    private BigDecimal actualProfit;
    private BigDecimal potentialProfit;
}
