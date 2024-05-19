package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.entity.Category;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
class CategoryRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    @Rollback
    void assertThatAddCategoryWorksCorrectly() {
        Category category = new Category()
            .setCategoryName("Test Category");

        category = categoryRepository.save(category);

        assertThat(categoryRepository.findById(category.getId())).isPresent();
    }

    @Test
    @Transactional
    @Rollback
    void assertThatUpdateCategoryWorksCorrectly() {
        Category category = new Category()
            .setCategoryName("Test Category");
        category = categoryRepository.save(category);

        String updatedName = "Updated Test Category";
        category.setCategoryName(updatedName);
        categoryRepository.save(category);

        Optional<Category> updatedCategoryOptional = categoryRepository.findById(category.getId());
        assertThat(updatedCategoryOptional).isPresent();
        assertThat(updatedCategoryOptional.get().getCategoryName()).isEqualTo(updatedName);
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatDeleteCategoryWorksCorrectly() {
        Category category = new Category()
            .setCategoryName("Test Category");
        category = categoryRepository.save(category);

        categoryRepository.delete(category);

        assertThat(categoryRepository.findById(category.getId())).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllCategoriesWorksCorrectly() {
        // Create multiple categories
        Category category1 = new Category().setCategoryName("Category 1");
        Category category2 = new Category().setCategoryName("Category 2");
        categoryRepository.saveAll(Arrays.asList(category1, category2));

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).hasSize(2);
        assertThat(categories).contains(category1, category2);
    }

}
