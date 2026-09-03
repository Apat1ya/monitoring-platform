package io.github.apat1ya.incident.messaging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic changeStatusTopic() {
        return TopicBuilder
                .name("${app.kafka.topics.endpoint-status-changed}")
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    private NewTopic statusEvent() {
        return TopicBuilder
                .name("${app.kafka.topics.state-changed}")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
