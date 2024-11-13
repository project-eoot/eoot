package com.ssafy.eoot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class TokenConfig {

    private final TokenProperties tokenProperties;

    @Bean
    public SecretKeySpec secretKeySpec(){
        String key = "12345678912345678912345678912345";
        return new SecretKeySpec(key.getBytes(), "HmacSHA256");
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec()).build();

        OAuth2TokenValidator<Jwt> validators = new DelegatingOAuth2TokenValidator<>(
                new JwtIssuerValidator(tokenProperties.getIssuer()),
                new JwtTimestampValidator(Duration.ofSeconds(1))
        );
        jwtDecoder.setJwtValidator(validators);

        return jwtDecoder;
    }
}
