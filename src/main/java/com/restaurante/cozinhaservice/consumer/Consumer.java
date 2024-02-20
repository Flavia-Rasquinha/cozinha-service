package com.restaurante.cozinhaservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.cozinhaservice.client.CallOrderClient;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import com.restaurante.cozinhaservice.service.DishService;
import com.restaurante.cozinhaservice.service.StockService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private ObjectMapper objectMapper;
    private CallOrderClient callOrderClient;
    private DishService dishService;
    private StockService stockService;

    @KafkaListener(topics = "pedidos")
    public void consume(String message) throws IOException {
        var orderDto = objectMapper.readValue(message, OrderDto.class);

        logger.info("Recebido pedido IdPedido: {}", orderDto.id());

        AtomicReference<OrderDto> updateOrder = dishService.verifyOrder(orderDto);

        if (updateOrder.get().status().equals("CANCELADO")) {
            callOrderClient.callOrder(updateOrder.get().id(), updateOrder.get().status());
        } else {
            stockService.removeItemStock(orderDto);
            updateOrder.set(orderDto.withStatus("PRONTO"));
            callOrderClient.callOrder(updateOrder.get().id(), updateOrder.get().status());
        }

        logger.info("Pedido IdPedido: {}, processado para status: {}", orderDto.id(), updateOrder.get().status());
    }

}