package com.restaurante.cozinhaservice.dto;


import com.restaurante.cozinhaservice.enums.StatusEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderDto(String id, List<ItemsOrdenDto> items, BigDecimal totalValue, StatusEnum status) {

    public OrderDto withStatus(StatusEnum status) {
        return new OrderDto(id(), items(), totalValue(), status);
    }
}

