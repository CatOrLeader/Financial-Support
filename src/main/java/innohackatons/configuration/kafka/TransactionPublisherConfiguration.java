package innohackatons.configuration.kafka;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.configuration.ApplicationConfiguration;
import innohackatons.kafka.KafkaQueueProducer;
import innohackatons.repository.BankRepository;
import innohackatons.repository.CategoryRepository;
import innohackatons.repository.DepositRepository;
import innohackatons.repository.TransactionRepository;
import innohackatons.repository.UserRepository;
import innohackatons.service.implementation.TransactionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class TransactionPublisherConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
    public KafkaQueueProducer scrapperQueueProducer(
        ApplicationConfiguration config,
        KafkaTemplate<Long, PostTransactionRequest> kafkaTemplate
    ) {
        return new KafkaQueueProducer(config, kafkaTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public TransactionServiceImpl clientLinkUpdateSender(
        TransactionRepository transactionRepository,
        UserRepository userRepository,
        BankRepository bankRepository,
        CategoryRepository categoryRepository,
        DepositRepository depositRepository
    ) {
        return new TransactionServiceImpl(
            transactionRepository,
            userRepository,
            bankRepository,
            categoryRepository,
            depositRepository
        );
    }
}
