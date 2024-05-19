package innohackatons;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.configuration.ApplicationConfiguration;
import innohackatons.entity.Category;
import innohackatons.entity.User;
import innohackatons.kafka.TransactionConsumer;
import innohackatons.repository.CategoryRepository;
import innohackatons.repository.UserRepository;
import java.time.LocalDateTime;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Disabled
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
class KafkaIntegrationTest extends IntegrationEnvironment {

    @Autowired
    private KafkaTemplate<Long, PostTransactionRequest> kafkaTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionConsumer consumer;

    @Autowired
    private ApplicationConfiguration config;

    private User user;
    private Category category;

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.enabled", () -> "false");
    }

    @BeforeEach
    public void setUp() {
        user = userRepository.save(new User().setName("Test User"));
        category = categoryRepository.save(new Category().setCategoryName("Test Category"));
    }

    @Test
    void testSendAndReceive() {
        PostTransactionRequest request =
            new PostTransactionRequest(user.getId(), 1L, category.getId(), 10000, LocalDateTime.now());

        kafkaTemplate.send(config.kafkaConfigInfo().updatesTopic().name(), request.userId(), request);

        Awaitility.await().atMost(Duration.TWO_SECONDS).untilAsserted(() -> assertThat(consumer).isNotNull());
    }

    @Test
    void testConsumerHandlesMultipleMessages() {
        for (int i = 0; i < 5; i++) {
            PostTransactionRequest request =
                new PostTransactionRequest(user.getId(), 1L, category.getId(), 10000, LocalDateTime.now());
            kafkaTemplate.send(config.kafkaConfigInfo().updatesTopic().name(), request.userId(), request);
        }

        // wait until the consumer processes all messages
        await().untilAsserted(() -> {
            // add assertions to verify the processing of each message
        });
    }
}
