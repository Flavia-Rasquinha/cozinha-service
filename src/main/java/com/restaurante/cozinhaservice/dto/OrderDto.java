package com.restaurante.cozinhaservice.dto;


import java.math.BigDecimal;
import java.util.List;

public record OrderDto(String id, List<ItemsDto> items, BigDecimal totalValue, String status) {

    public OrderDto withStatus(String status) {
        return new OrderDto(id(), items(), totalValue(), status);
    }
}

