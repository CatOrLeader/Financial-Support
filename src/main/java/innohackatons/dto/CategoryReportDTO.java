package innohackatons.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
