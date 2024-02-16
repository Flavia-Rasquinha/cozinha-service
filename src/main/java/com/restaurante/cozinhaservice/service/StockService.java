package com.restaurante.cozinhaservice.service;

import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class StockService {

    private DishRepository dishRepository;
    private StockRepository stockRepository;

    public void removeItemStock(OrderDto orderDto) {
        orderDto.items().forEach(item -> {
            if (Objects.nonNull(item.idDish())) {
                var dish = dishRepository.findById(item.idDish());

                dish.ifPresent(dishEntity -> dishEntity.getItems().forEach(itemDish -> {
                    var findStock = stockRepository.findByIdIngredient(itemDish.idIngredient());
                    findStock.ifPresent(stockEntity -> {
                        stockEntity.setAmount(stockEntity.getAmount() - itemDish.amount());
                        stockRepository.save(findStock.get());
                    });
                }));
            } else {
                var findStock = stockRepository.findByIdIngredient(item.idIngredient());
                findStock.ifPresent(stockEntity -> {
                    stockEntity.setAmount(stockEntity.getAmount() - item.amount());
                    stockRepository.save(findStock.get());
                });
            }
        });
    }
}
