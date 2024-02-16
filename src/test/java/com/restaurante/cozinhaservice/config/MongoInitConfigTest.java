package com.restaurante.cozinhaservice.config;

import com.restaurante.cozinhaservice.config.MongoInitConfig;
import com.restaurante.cozinhaservice.dto.ItemsDto;
import com.restaurante.cozinhaservice.dto.ItemsOrdenDto;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.entity.DishEntity;
import com.restaurante.cozinhaservice.entity.IngredientEntity;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.IngredientRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;

class MongoInitConfigTest {

    @InjectMocks
    private MongoInitConfig mongoInitConfig;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDish() {

        Mockito.when(dishRepository.findAll()).thenReturn(Collections.singletonList(DishEntity.builder().build()));

        mongoInitConfig.initMongoDishs();

        Mockito.verify(dishRepository, Mockito.times(1)).saveAll(any());
    }

    @Test
    public void createIngredient() {

        Mockito.when(ingredientRepository.findAll()).thenReturn(Collections.singletonList(IngredientEntity.builder().build()));

        mongoInitConfig.initIngredient();

        Mockito.verify(ingredientRepository, Mockito.times(1)).saveAll(any());
    }

    @Test
    public void createStock() {

        Mockito.when(stockRepository.findAll()).thenReturn(Collections.singletonList(StockEntity.builder().build()));

        mongoInitConfig.initMongoStock();

        Mockito.verify(stockRepository, Mockito.times(1)).saveAll(any());
    }
    private static OrderDto createOrder() {
        OrderDto orderDto = OrderDto.builder()
                .items(Collections.singletonList(ItemsOrdenDto.builder()
                        .id("1")
                        .idIngredient("feijao1")
                        .amount(1)
                        .value(BigDecimal.TEN)
                        .build()))
                .totalValue(BigDecimal.ONE)
                .build();
        return orderDto;
    }
}