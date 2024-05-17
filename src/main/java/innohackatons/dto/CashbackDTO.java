package innohackatons.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CashbackDTO {
    @NotNull
    private Long bankId;

    @NotNull
    private Long categoryId;

    @Min(value = 0, message = "The ratio must be at least 0.0")
    @Max(value = 1, message = "The ratio must be at most 1.0")
    @NotNull
    private Double ratio;
}
