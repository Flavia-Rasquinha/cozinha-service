package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class DishService {

    private DishRepository dishRepository;
    private StockRepository stockRepository;
    public AtomicReference<OrderDto> verifyOrder(OrderDto orderDto) {
        AtomicReference<OrderDto> updateOrder = new AtomicReference<>(orderDto.withStatus("EM_ANDAMENTO"));
        orderDto.items().forEach(item -> {

            if (Objects.nonNull(item.idDish())) {
                var dish = dishRepository.findById(item.idDish());

                dish.ifPresent(dishEntity -> dishEntity.getItems().forEach(itemDish -> {
                    var itemStock = stockRepository.findByIdIngredient(itemDish.idIngredient());
                    verifyCanceled(itemStock.get(), updateOrder, orderDto, itemDish.amount());
                }));
            } else {
                var itemStock = stockRepository.findByIdIngredient(item.idIngredient());
                verifyCanceled(itemStock.get(), updateOrder, orderDto, item.amount());
            }
        });
        return updateOrder;
    }

    public void verifyCanceled(StockEntity itemStock, AtomicReference<OrderDto> updateOrder,
                               OrderDto orderDto, int itemDish) {
        if (itemStock.getAmount() < itemDish) {
            updateOrder.set(orderDto.withStatus("CANCELADO"));
        }
    }
}
