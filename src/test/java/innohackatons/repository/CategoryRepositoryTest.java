package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class CategoryRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @Rollback
    public void assertThatAddCategoryWorksCorrectly() {
        Category category = new Category()
        .setCategoryName("Test Category");

        category = categoryRepository.save(category);

        assertThat(categoryRepository.findById(category.getCategoryId())).isPresent();
    }
}
