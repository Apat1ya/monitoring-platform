package io.github.apat1ya.auth.config;

import io.github.apat1ya.auth.dto.EmailChangeData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, EmailChangeData> emailChangeRedisTemplate(
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, EmailChangeData> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(
                new JacksonJsonRedisSerializer<>(EmailChangeData.class)
        );

        template.afterPropertiesSet();

        return template;
    }
}
