package com.restaurante.cozinhaservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.cozinhaservice.client.CallOrderClient;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
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
    private DishRepository dishRepository;
    private StockRepository stockRepository;

    @KafkaListener(topics = "pedido")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));

        var orderDto = objectMapper.readValue(message, OrderDto.class);

        AtomicReference<OrderDto> updateOrder = verifyOrder(orderDto);

        removeItemStock(orderDto);

        if (orderDto.status().equals("CANCELADO")) {
            callOrderClient.callOrder(updateOrder.get());
        } else {
            updateOrder.set(orderDto.withStatus("PRONTO"));
        }
    }

    private AtomicReference<OrderDto> verifyOrder(OrderDto orderDto) {
        AtomicReference<OrderDto> updateOrder = new AtomicReference<>(orderDto.withStatus("EM_ANDAMENTO"));
        orderDto.items().forEach(item -> {

            var dish = dishRepository.findByDishName(item.name());

            dish.ifPresent(dishEntity -> dishEntity.getItems().forEach(itemDish -> {
                var itemStock = stockRepository.findByProductName(itemDish.name());
                verifyCanceled(itemStock, updateOrder, orderDto);
            }));

            if (dish.isEmpty()) {
                var itemStock = stockRepository.findByProductName(item.name());
                verifyCanceled(itemStock, updateOrder, orderDto);
            }
        });
        return updateOrder;
    }

    private void verifyCanceled(Optional<StockEntity> itemStock, AtomicReference<OrderDto> updateOrder, OrderDto orderDto) {
        if (itemStock.isEmpty()) {
            updateOrder.set(orderDto.withStatus("CANCELADO"));
        }
    }

    private void removeItemStock(OrderDto orderDto) {
        orderDto.items().forEach(item -> {
            var dish = dishRepository.findByDishName(item.name());

            dish.ifPresent(dishEntity -> dishEntity.getItems().forEach(itemDish -> {
                var findStock = stockRepository.findByProductName(itemDish.name());
                findStock.ifPresent(stockEntity -> {
                    stockEntity.setAmount(stockEntity.getAmount() - itemDish.amount());
                    stockRepository.save(findStock.get());
                });
            }));

            var findStock = stockRepository.findByProductName(item.name());
            findStock.ifPresent(stockEntity -> {
                stockEntity.setAmount(stockEntity.getAmount() - item.amount());
                stockRepository.save(findStock.get());
            });
        });
    }

}