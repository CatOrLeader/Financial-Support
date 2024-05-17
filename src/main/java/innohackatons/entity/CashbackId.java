package innohackatons.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable public class CashbackId implements Serializable {
    private UUID bankId;
    private UUID categoryId;
}
