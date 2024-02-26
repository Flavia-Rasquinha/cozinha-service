package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.ItemsOrdenDto;
import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class StockService {
    private final Logger logger = LoggerFactory.getLogger(StockService.class);
    private DishRepository dishRepository;
    private StockRepository stockRepository;

    public void removeItemStock(OrderDto orderDto) {
        logger.info("Removing items from inventory");

        orderDto.items().forEach(item -> {
            if (Objects.nonNull(item.idDish())) {
                var dish = dishRepository.findById(item.idDish());

                dish.ifPresent(dishEntity -> dishEntity.getItems().forEach(itemDish -> {
                    removeByItem(itemDish.idIngredient(), itemDish.amount());
                }));
            } else {
                removeByItem(item.idIngredient(), item.amount());
            }
        });
    }

    private void removeByItem(String idIngredient, Integer amount) {
        var findStock = stockRepository.findByIdIngredient(idIngredient);
        findStock.ifPresent(stockEntity -> {
            stockEntity.setAmount(stockEntity.getAmount() - amount);
            stockRepository.save(findStock.get());
        });
    }
}
