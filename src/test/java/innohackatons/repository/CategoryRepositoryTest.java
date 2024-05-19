package innohackatons.repository;

import innohackatons.IntegrationEnvironment;
import innohackatons.configuration.kafka.KafkaConfiguration;
import innohackatons.entity.Category;
import innohackatons.kafka.TransactionConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
class CategoryRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private CategoryRepository categoryRepository;
    @MockBean
    private KafkaConfiguration kafkaConfiguration;
    @MockBean
    private TransactionConsumer transactionConsumer;

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
    void assertThatDeleteCategoryWorksCorrectly() {
        Category category = new Category()
            .setCategoryName("Test Category");
        category = categoryRepository.save(category);

        categoryRepository.delete(category);

        assertThat(categoryRepository.findById(category.getId())).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void assertThatFindAllCategoriesWorksCorrectly() {
        // Create multiple categories
        Category category1 = new Category().setCategoryName("Category 1");
        Category category2 = new Category().setCategoryName("Category 2");
        categoryRepository.saveAll(Arrays.asList(category1, category2));

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).hasSize(2).contains(category1, category2);
    }

}
