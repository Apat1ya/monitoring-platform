package io.github.apat1ya.incident;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSmokeConsumer {

    @KafkaListener(topics = "test.kafka-smoke")
    public void consume(String message) {
        System.out.println("KAFKA RECEIVED >>> " + message);
    }
}
