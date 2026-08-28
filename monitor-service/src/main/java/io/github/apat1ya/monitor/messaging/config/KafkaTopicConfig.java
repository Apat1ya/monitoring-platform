package io.github.apat1ya.monitor.messaging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic checkResultsTopic() {
        return TopicBuilder
                .name("${app.kafka.topics.check-results}")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
