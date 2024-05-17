package innohackatons.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CashbackDTO {
    private UUID bankId;
    private UUID categoryId;
    private Double ratio;
}
