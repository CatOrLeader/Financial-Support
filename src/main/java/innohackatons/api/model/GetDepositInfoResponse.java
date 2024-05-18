package innohackatons.api.model;

import jakarta.validation.constraints.Min;

public record GetDepositInfoResponse(
    @Min(0) long bankId,
    @Min(0) double amount
) {
}
