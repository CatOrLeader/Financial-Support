package innohackatons.dto;

import lombok.Data;

@Data
public class DepositDTO {
    private Long id;
    private Long userId;
    private Long bankId;
    private Double amount;
}
