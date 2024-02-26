package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.ItemsDto;
import com.restaurante.cozinhaservice.dto.ItemsOrdenDto;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.entity.DishEntity;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.enums.StatusEnum;
import com.restaurante.cozinhaservice.repository.DishRepository;
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

class DishServiceTest {

    @InjectMocks
    private DishService dishService;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void verifyOrderSuccess() {

        Mockito.when(dishRepository.findById(any())).thenReturn(Optional.ofNullable(DishEntity.builder().build()));
        Mockito.when(stockRepository.findByIdIngredient(any())).thenReturn(Optional.ofNullable(StockEntity.builder().amount(1).build()));

        OrderDto orderDto = createOrder();
        AtomicReference<OrderDto> orderDtoAtomicReference = dishService.verifyOrder(orderDto);

        Assertions.assertEquals(StatusEnum.REQUESTED, orderDtoAtomicReference.get().status());
    }

    @Test
    public void verifyOrderCanceled() {

        Mockito.when(dishRepository.findById(any())).thenReturn(Optional.ofNullable(DishEntity.builder()
                .items(Collections.singletonList(ItemsDto.builder()
                        .idIngredient("arroz1")
                        .amount(1)
                        .build()))
                .build()));
        Mockito.when(stockRepository.findByIdIngredient(any())).thenReturn(Optional.ofNullable(StockEntity.builder().build()));

        OrderDto orderDto = createOrderDish();
        AtomicReference<OrderDto> orderDtoAtomicReference = dishService.verifyOrder(orderDto);

        Assertions.assertEquals(StatusEnum.CANCELED, orderDtoAtomicReference.get().status());
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
                .status(StatusEnum.REQUESTED)
                .build();
        return orderDto;
    }

    private static OrderDto createOrderError() {
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

    private static OrderDto createOrderDish() {
        OrderDto orderDto = OrderDto.builder()
                .items(Collections.singletonList(ItemsOrdenDto.builder()
                        .id("1")
                        .idDish("alaminuta1")
                        .amount(1)
                        .value(BigDecimal.TEN)
                        .build()))
                .totalValue(BigDecimal.ONE)
                .build();
        return orderDto;
    }

}