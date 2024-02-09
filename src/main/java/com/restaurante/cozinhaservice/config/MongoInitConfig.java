package com.restaurante.cozinhaservice.config;

import com.restaurante.cozinhaservice.dto.ItemsDto;
import com.restaurante.cozinhaservice.entity.DishEntity;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "com.restaurante.cozinhaservice")
@RequiredArgsConstructor
public class MongoInitConfig {

    private final DishRepository dishRepository;
    private final StockRepository stockRepository;

    @Bean
    void initMongoDishs() {

        List<DishEntity> allDish = dishRepository.findAll();

        var dishs = new ArrayList<DishEntity>();
        var items = new ArrayList<ItemsDto>();
        var itemsPasta = new ArrayList<ItemsDto>();


        var itemsDtoRice = new ItemsDto(1L, "Arroz", 4, BigDecimal.TEN);
        var itemsDtoEgg = new ItemsDto(2L, "Ovo", 4, BigDecimal.TEN);
        var itemsDtoMeat = new ItemsDto(3L, "Carne", 4, BigDecimal.TEN);

        items.add(itemsDtoRice);
        items.add(itemsDtoEgg);
        items.add(itemsDtoMeat);

        var itemsDtoPasta= new ItemsDto(4L, "Massa", 4, BigDecimal.TEN);
        var itemsDtoCheese = new ItemsDto(5L, "Molho Quatro Queijos", 4, BigDecimal.TEN);

        itemsPasta.add(itemsDtoPasta);
        itemsPasta.add(itemsDtoCheese);

        var dish = DishEntity.builder().dishName("Alaminuta").items(items).build();
        var dishPasta = DishEntity.builder().dishName("Massa").items(itemsPasta).build();

        dishs.add(dish);
        dishs.add(dishPasta);

        allDish.forEach(allD -> dishs.removeIf(d -> d.getDishName().equals(allD.getDishName())));

        dishRepository.saveAll(dishs);
    }

    @Bean
    void initMongoStock() {

        List<StockEntity> allStock = stockRepository.findAll();

        var stocks = new ArrayList<StockEntity>();

        var stockCoca = StockEntity.builder().productName("Coca").amount(3).build();
        var stockBean = StockEntity.builder().productName("Feijão").amount(2).build();
        var stockRice = StockEntity.builder().productName("Arroz").amount(3).build();
        var stockEgg = StockEntity.builder().productName("Ovo").amount(2).build();
        var stockMeat = StockEntity.builder().productName("Carne").amount(3).build();
        var stockPasta = StockEntity.builder().productName("Massa").amount(2).build();
        var stockCheese = StockEntity.builder().productName("Molho Quatro Queijos").amount(3).build();

        stocks.add(stockCoca);
        stocks.add(stockBean);
        stocks.add(stockRice);
        stocks.add(stockEgg);
        stocks.add(stockMeat);
        stocks.add(stockPasta);
        stocks.add(stockCheese);

        allStock.forEach(allS -> stocks.removeIf(s -> s.getProductName().equals(allS.getProductName())));

        stockRepository.saveAll(stocks);
    }
}