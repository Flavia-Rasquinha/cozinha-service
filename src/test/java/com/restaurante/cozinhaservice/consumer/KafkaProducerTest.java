package com.restaurante.cozinhaservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.cozinhaservice.client.CallOrderClient;
import com.restaurante.cozinhaservice.dto.ItemsOrdenDto;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.enums.StatusEnum;
import com.restaurante.cozinhaservice.service.DishService;
import com.restaurante.cozinhaservice.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.*;

public class KafkaProducerTest {

    @InjectMocks
    private Consumer consumer;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private DishService dishService;
    @Mock
    private StockService stockService;
    @Mock
    private CallOrderClient callOrderClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void consumerWithOrderCanceledShouldReturnSuccess() throws IOException {

        Mockito.when(objectMapper.readValue(anyString(), eq(OrderDto.class))).thenReturn(OrderDto.builder().build());
        Mockito.when(dishService.verifyOrder(any())).thenReturn(createOrderCanceled());
        Mockito.doNothing().when(callOrderClient).callOrder(anyString(), any());

        consumer.consume("mensagem");

        Mockito.verify(callOrderClient, Mockito.times(1)).callOrder(ArgumentMatchers.anyString(), ArgumentMatchers.any());
    }

    @Test
    public void consumerWithOrderShouldReturnSuccess() throws IOException {

        Mockito.when(objectMapper.readValue(anyString(), eq(OrderDto.class))).thenReturn(OrderDto.builder().id("1").build());
        Mockito.when(dishService.verifyOrder(any())).thenReturn(createOrder());
        Mockito.doNothing().when(callOrderClient).callOrder(anyString(), any());

        consumer.consume("mensagem");

        Mockito.verify(stockService, Mockito.times(1)).removeItemStock(any());
        Mockito.verify(callOrderClient, Mockito.times(1)).callOrder(ArgumentMatchers.anyString(), ArgumentMatchers.any());
    }

    private static AtomicReference<OrderDto> createOrderCanceled() {
        AtomicReference<OrderDto> orderDto = new AtomicReference<>(OrderDto.builder()
                .id("1")
                .items(Collections.singletonList(ItemsOrdenDto.builder()
                        .id("1")
                        .idProduct("feijao1")
                        .amount(1)
                        .value(BigDecimal.TEN)
                        .build()))
                .totalValue(BigDecimal.ONE)
                .status(StatusEnum.CANCELED)
                .build());
        return orderDto;
    }

    private static AtomicReference<OrderDto> createOrder() {
        AtomicReference<OrderDto> orderDto = new AtomicReference<>(OrderDto.builder()
                .id("1")
                .items(Collections.singletonList(ItemsOrdenDto.builder()
                        .id("1")
                        .idProduct("feijao1")
                        .amount(1)
                        .value(BigDecimal.TEN)
                        .build()))
                .totalValue(BigDecimal.ONE)
                .status(StatusEnum.READY)
                .build());
        return orderDto;
    }
}