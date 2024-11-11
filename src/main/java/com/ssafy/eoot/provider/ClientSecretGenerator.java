package com.ssafy.eoot.provider;

public interface ClientSecretGenerator {
    String getRegistrationId();
    String generate();
}
