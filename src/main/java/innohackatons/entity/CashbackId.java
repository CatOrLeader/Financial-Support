package innohackatons.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode(of = {"bankId", "categoryId"})
public class CashbackId implements Serializable {
    private Long bankId;
    private Long categoryId;
}
