package innohackatons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReportDTO {
    private Long categoryId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;
    private Long recommendedBankId;
    private Double amountSpent;
    private Double actualProfit;
    private Double potentialProfit;
}
