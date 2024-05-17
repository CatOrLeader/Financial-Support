package innohackatons.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BankDTO {
    private UUID id;
    private String bankName;
}
