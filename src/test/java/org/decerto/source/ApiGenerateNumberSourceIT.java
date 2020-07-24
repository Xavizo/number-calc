package org.decerto.source;

import org.decerto.source.dto.RandomOrgRequest;
import org.decerto.source.impl.ApiGenerateNumberSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("integration")
@Tag("integration")
class ApiGenerateNumberSourceIT {
    @MockBean
    RestTemplate restTemplate;

    @Autowired
    ApiGenerateNumberSource apiGenerateNumberSource;

    @Test
    @DisplayName(value = "Invoke API endpoint for random value")
    void whenInvokeGetValue_generateRandomNumber() {
        //given
        String successJson = "{\"jsonrpc\":\"2.0\",\"result\":{\"random\":{\"data\":[324],\"completionTime\":\"2020-07-24 09:44:37Z\"},\"bitsUsed\":10,\"bitsLeft\":249343,\"requestsLeft\":952,\"advisoryDelay\":1050},\"id\":14142}";
        given(restTemplate.postForEntity(anyString(), any(RandomOrgRequest.class), any()))
                .willReturn(ResponseEntity.ok(successJson));

        //when
        Integer value = apiGenerateNumberSource.getValue();

        //then
        assertThat(value).isNotNull();
        assertThat(value).isEqualTo(324);
    }

    @Test
    @DisplayName(value = "Invoke API endpoint and handle error")
    void whenInvokeGetValue_throwExceptionWithDetailedMessage() {
        //given
        String errorJson = "{\"jsonrpc\":\"2.0\",\"error\":{\"code\":200,\"message\":\"Parameter 'n' is malformed\",\"data\":[\"n\"]},\"id\":14142}";
        given(restTemplate.postForEntity(anyString(), any(RandomOrgRequest.class), any()))
                .willReturn(ResponseEntity.ok(errorJson));

        //then
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> apiGenerateNumberSource.getValue())
                .withMessage("Parameter 'n' is malformed");

    }
}