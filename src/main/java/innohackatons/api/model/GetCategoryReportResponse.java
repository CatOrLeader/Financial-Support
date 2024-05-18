package innohackatons.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import innohackatons.service.dto.CategoryReportDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GetCategoryReportResponse(
    @NotBlank String categoryName,
    @NotNull LocalDateTime dateFrom,
    @NotNull LocalDateTime dateTo,
    @Min(0) long recommendedBankId,
    @Min(0) BigDecimal amountSpent,
    @Min(0) BigDecimal actualProfit,
    @Min(0) BigDecimal potentialProfit
) {
    public GetCategoryReportResponse(@NotNull CategoryReportDTO reportDTO) {
        this(
            reportDTO.getCategoryId().toString(),
            reportDTO.getDateFrom(),
            reportDTO.getDateTo(),
            reportDTO.getRecommendedBankId(),
            reportDTO.getAmountSpent(),
            reportDTO.getActualProfit(),
            reportDTO.getPotentialProfit()
        );
    }
}
