package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.ItemsDto;
import com.restaurante.cozinhaservice.dto.ItemsOrdenDto;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.entity.DishEntity;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;

class StockServiceTest {

    @InjectMocks
    private StockService stockService;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void removeItemStockWithValidOrderShouldReturnSuccess() {

        Mockito.when(dishRepository.findById(any())).thenReturn(Optional.ofNullable(DishEntity.builder().build()));
        Mockito.when(stockRepository.findByIdIngredient(any())).thenReturn(Optional.ofNullable(StockEntity.builder().amount(1).build()));

        OrderDto orderDto = createOrder();
        stockService.removeItemStock(orderDto);

        Mockito.verify(stockRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void removeItemStockDishWithValidDishShouldReturnSuccess() {

        Mockito.when(dishRepository.findById(any())).thenReturn(Optional.ofNullable(DishEntity.builder()
                .items(Collections.singletonList(ItemsDto.builder()
                        .idIngredient("arroz1")
                        .amount(1)
                        .build()))
                .build()));
        Mockito.when(stockRepository.findByIdIngredient(any())).thenReturn(Optional.ofNullable(StockEntity.builder().amount(1).build()));

        OrderDto orderDto = createOrderDish();

        stockService.removeItemStock(orderDto);

        Mockito.verify(stockRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void removeItemStockWithInvalidDishShouldReturnError() {

        Mockito.when(dishRepository.findById(any())).thenReturn(Optional.ofNullable(DishEntity.builder()
                .items(Collections.singletonList(ItemsDto.builder()
                        .idIngredient("arroz1")
                        .amount(1)
                        .build()))
                .build()));

        OrderDto orderDto = createOrderDish();

        stockService.removeItemStock(orderDto);

        Mockito.verify(stockRepository, Mockito.times(0)).save(any());
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