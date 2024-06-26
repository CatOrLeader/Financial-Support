package innohackatons.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReportDTO {
    private Long categoryId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long recommendedBankId;
    private BigDecimal amountSpent;
    private BigDecimal actualProfit;
    private BigDecimal potentialProfit;
}
