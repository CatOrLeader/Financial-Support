package innohackatons.configuration.kafka;

import innohackatons.api.model.PostTransactionRequest;
import innohackatons.configuration.ApplicationConfiguration;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConfiguration {
    @Bean
    public NewTopic transactionsTopic(ApplicationConfiguration config) {
        return TopicBuilder
            .name(config.kafkaConfigInfo().updatesTopic().name())
            .partitions(config.kafkaConfigInfo().updatesTopic().partitions())
            .replicas(config.kafkaConfigInfo().updatesTopic().replicas())
            .build();
    }

    @Bean
    public ConsumerFactory<Long, PostTransactionRequest> transactionConsumerFactory(ApplicationConfiguration config) {
        return new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfigInfo().bootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
            JsonDeserializer.TRUSTED_PACKAGES, "*",
            JsonDeserializer.USE_TYPE_INFO_HEADERS, false,
            JsonDeserializer.VALUE_DEFAULT_TYPE, PostTransactionRequest.class
        ));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, PostTransactionRequest>
    transactionConcurrentKafkaListenerContainerFactory(ConsumerFactory<Long, PostTransactionRequest> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, PostTransactionRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ProducerFactory<Long, PostTransactionRequest> producerFactory(ApplicationConfiguration config) {
        return new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfigInfo().bootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
            JsonSerializer.ADD_TYPE_INFO_HEADERS, false
        ));
    }

    @Bean
    public KafkaTemplate<Long, PostTransactionRequest> kafkaTemplate(
        ProducerFactory<Long, PostTransactionRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
