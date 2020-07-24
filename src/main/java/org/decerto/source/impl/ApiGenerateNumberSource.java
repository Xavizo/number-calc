package org.decerto.source.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.decerto.source.InputSource;
import org.decerto.source.config.RandomApiProperties;
import org.decerto.source.dto.GenerateIntegerParams;
import org.decerto.source.dto.RandomOrgMethod;
import org.decerto.source.dto.RandomOrgRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class ApiGenerateNumberSource implements InputSource<Integer> {
    private final RandomApiProperties properties;
    private final RestTemplate restTemplate;

    public ApiGenerateNumberSource(RandomApiProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Integer getValue() {
        return Optional.ofNullable(restTemplate
                .postForEntity(properties.getUrl(), prepareRequestBody(), String.class).getBody())
                .map(this::parseJson)
                .filter(this::isResultInvalid)
                .map(this::extractValue)
                .orElseThrow(() -> new IllegalStateException("Response body is empty"));
    }

    private JsonObject parseJson(String body) {
        return new JsonParser()
                .parse(body)
                .getAsJsonObject();
    }


    private boolean isResultInvalid(JsonObject json) {
        if (json.has("error")) {
            JsonObject error = json.getAsJsonObject("error");
            log.error("Error on api call {}", error);

            throw new IllegalStateException(error.getAsJsonObject().get("message").getAsString());
        }
        return false;
    }

    private Integer extractValue(JsonObject json) {
        Number value = json.getAsJsonObject().get("result")
                .getAsJsonObject().get("random")
                .getAsJsonObject().getAsJsonArray("data")
                .get(0)
                .getAsNumber();

        return NumberUtils.convertNumberToTargetClass(value, Integer.class);
    }

    private RandomOrgRequest prepareRequestBody() {
        return RandomOrgRequest.builder()
                .id(ThreadLocalRandom.current().nextInt())
                .jsonrpc("2.0")
                .method(RandomOrgMethod.GENERATE_INTEGERS)
                .params(GenerateIntegerParams.builder()
                        .apiKey(properties.getApiKey())
                        .n(1)
                        .min(-999_999_999)
                        .max(999_999_999)
                        .build())
                .build();
    }
}
