package org.decerto.source.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomOrgRequest {
    private String jsonrpc;
    private RandomOrgMethod method;
    private Integer id;
    private GenerateIntegerParams params;
}
