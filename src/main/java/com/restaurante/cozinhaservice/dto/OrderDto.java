package com.restaurante.cozinhaservice.dto;


import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderDto(String id, List<ItemsOrdenDto> items, BigDecimal totalValue, String status) {

    public OrderDto withStatus(String status) {
        return new OrderDto(id(), items(), totalValue(), status);
    }
}

