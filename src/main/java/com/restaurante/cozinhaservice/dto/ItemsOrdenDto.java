package com.restaurante.cozinhaservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemsOrdenDto(String id, String idProduct, int amount, BigDecimal value) {

}
