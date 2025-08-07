package com.femi.tradingplatform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeResponse {
    private Long id;
    private String symbol;
    private Integer quantity;
    private String createdAt;
    private String updatedAt;
}
