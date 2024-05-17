package innohackatons.dto;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class TransactionDTO {
    private Long userId;
    private Long bankId;
    private Long categoryId;
    private Double amount;
    private OffsetDateTime date;
}
