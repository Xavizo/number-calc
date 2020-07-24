package org.decerto.source.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@Configuration
@ConfigurationProperties(prefix = "random-org-api")
@Getter
@Setter
@Validated
public class RandomApiProperties {
    private String url;
    @NotEmpty(message = "API key must not be empty")
    private String apiKey;
}
