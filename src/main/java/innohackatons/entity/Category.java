package innohackatons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "categoryId")
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue
    private UUID categoryId;

    @Column(nullable = false)
    private String categoryName;
}
