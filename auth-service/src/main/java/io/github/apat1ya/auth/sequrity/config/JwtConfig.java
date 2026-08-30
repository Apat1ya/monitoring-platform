package io.github.apat1ya.auth.sequrity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

@Configuration
public class JwtConfig {
    @Value("${jwt.private-key-location}")
    private Resource privateKey;
    @Value("${jwt.public-key-location}")
    private Resource  publicKey;

    @Bean
    public JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        return NimbusJwtEncoder.withKeyPair(publicKey,privateKey).build();
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public RSAPrivateKey privateKey() throws IOException {
        try (InputStream inputStream = privateKey.getInputStream()) {
            return Objects.requireNonNull(RsaKeyConverters.pkcs8().convert(inputStream));
        }
    }

    @Bean
    public RSAPublicKey publicKey() throws IOException {
        try (InputStream inputStream = publicKey.getInputStream()) {
            return Objects.requireNonNull(RsaKeyConverters.x509().convert(inputStream));
        }    }
}
