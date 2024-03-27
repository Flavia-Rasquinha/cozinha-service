package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockService {
    private final Logger logger = LoggerFactory.getLogger(StockService.class);
    private DishRepository dishRepository;
    private StockRepository stockRepository;

    public void removeItemStock(OrderDto orderDto) {
        logger.info("Removing items from inventory");

        orderDto.items().forEach(item -> {
            var dish = dishRepository.findById(item.idProduct());
            if (dish.isPresent()) {
                dish.get().getItems().forEach(itemDish -> {
                    removeByItem(itemDish.idIngredient(), itemDish.amount());
                });
            } else {
                removeByItem(item.idProduct(), item.amount());
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
