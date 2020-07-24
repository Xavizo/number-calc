package org.decerto.source.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateIntegerParams {
    private String apiKey;
    private Integer n;
    private Integer min;
    private Integer max;
}
