package innohackatons.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable public class CashbackId implements Serializable {
    private Long bankId;
    private Long categoryId;
}
