package innohackatons.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GetAllUsersInfoResponse(
    @NotNull List<GetUserInfoResponse> userInfoResponseList,
    @Min(0) int size
) {
}
