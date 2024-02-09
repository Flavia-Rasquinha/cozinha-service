package com.restaurante.cozinhaservice.dto;

import java.math.BigDecimal;

public record ItemsDto(Long id, String name, int amount, BigDecimal value) {

}
