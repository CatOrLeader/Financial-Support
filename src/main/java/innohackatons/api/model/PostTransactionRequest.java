package innohackatons.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PostTransactionRequest(
    @Min(0) long userId,
    @Min(0) long bankId,
    @Min(0) long categoryId,
    double amount,
    @NotNull LocalDateTime dateTime
) {
}
