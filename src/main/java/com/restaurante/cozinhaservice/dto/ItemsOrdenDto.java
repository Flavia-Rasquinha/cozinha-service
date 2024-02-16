package com.restaurante.cozinhaservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemsOrdenDto(String id, String idIngredient, String idDish, int amount, BigDecimal value) {

}
