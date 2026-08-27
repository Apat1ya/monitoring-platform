package io.github.apat1ya.monitor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSmokeRunner implements ApplicationRunner {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSmokeRunner(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        kafkaTemplate.send("test.kafka-smoke", "hello kafka");
    }
}
