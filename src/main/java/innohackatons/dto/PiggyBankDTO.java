package innohackatons.dto;

import lombok.Data;

@Data
public class PiggyBankDTO {
    private Long userId;
    private Double amount;
    private String goal;
}
