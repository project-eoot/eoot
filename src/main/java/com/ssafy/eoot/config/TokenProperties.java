package com.ssafy.eoot.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ConfigurationProperties("authorization.token")
@RequiredArgsConstructor
@Getter
public class TokenProperties {

    private final String issuer;
    private final List<String> audiences;

    @DurationUnit(ChronoUnit.SECONDS)
    private final Duration accessTokenTimeToLive;

    @DurationUnit(ChronoUnit.SECONDS)
    private final Duration refreshTokenTimeToLive;
}
