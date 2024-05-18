package innohackatons.dto;

import lombok.Data;

@Data
public class CashbackDTO {
    private Long userId;
    private Long bankId;
    private Long categoryId;
    private Double ratio;
}
