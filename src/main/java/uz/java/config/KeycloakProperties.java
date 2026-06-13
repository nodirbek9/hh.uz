package uz.java.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.keycloak")
public record KeycloakProperties(
        String keycloakServerUrl,
        String masterRealm,
        String realm,
        String adminUsername,
        String adminClientId,
        String clientId,
        String clientSecret,
        String adminPassword) {
}
