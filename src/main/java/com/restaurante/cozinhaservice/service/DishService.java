package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.enums.StatusEnum;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class DishService {

    private final Logger logger = LoggerFactory.getLogger(DishService.class);
    private DishRepository dishRepository;
    private StockRepository stockRepository;
    public AtomicReference<OrderDto> verifyOrder(OrderDto orderDto) {
        logger.info("Checking order status: {}", orderDto.id());

        AtomicReference<OrderDto> updateOrder = new AtomicReference<>(orderDto);
        orderDto.items().forEach(item -> {

            if (Objects.nonNull(item.idDish())) {
                var dish = dishRepository.findById(item.idDish());

                dish.ifPresent(dishEntity -> dishEntity.getItems().forEach(itemDish -> {
                    verifyCanceled(itemDish.idIngredient(), updateOrder, orderDto, itemDish.amount());
                }));
            } else {
                verifyCanceled(item.idIngredient(), updateOrder, orderDto, item.amount());
            }
        });
        return updateOrder;
    }

    public void verifyCanceled(String idIngredient, AtomicReference<OrderDto> updateOrder,
                               OrderDto orderDto, int itemDish) {
        var itemStock = stockRepository.findByIdIngredient(idIngredient);

        if (itemStock.isPresent()) {
            if (itemStock.get().getAmount() < itemDish) {
                updateOrder.set(orderDto.withStatus(StatusEnum.CANCELED));
            }
        } else {
            updateOrder.set(orderDto.withStatus(StatusEnum.CANCELED));
        }
    }
}
