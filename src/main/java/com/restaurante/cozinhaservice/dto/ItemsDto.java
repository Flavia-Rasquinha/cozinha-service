package com.restaurante.cozinhaservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ItemsDto(String id, String idIngredient, Integer amount, BigDecimal value) {

}
