package innohackatons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"bank", "category"})
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "Cashback")
public class Cashback {
    @EmbeddedId
    private CashbackId id;

    @ManyToOne
    @MapsId("bankId")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @MapsId("categoryId")
    private Category category;

    @Column(nullable = false)
    private BigDecimal ratio;
}

