package com.restaurante.cozinhaservice.config;

import com.restaurante.cozinhaservice.dto.ItemsDto;
import com.restaurante.cozinhaservice.entity.DishEntity;
import com.restaurante.cozinhaservice.entity.IngredientEntity;
import com.restaurante.cozinhaservice.entity.StockEntity;
import com.restaurante.cozinhaservice.repository.DishRepository;
import com.restaurante.cozinhaservice.repository.IngredientRepository;
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
    private final IngredientRepository ingredientRepository;


    @Bean
    public void initMongoDishs() {

        List<DishEntity> allDish = dishRepository.findAll();

        var dishs = new ArrayList<DishEntity>();
        var items = new ArrayList<ItemsDto>();
        var itemsPasta = new ArrayList<ItemsDto>();


        var itemsDtoRice = new ItemsDto("1", "arroz1", 1, BigDecimal.TEN);
        var itemsDtoEgg = new ItemsDto("2", "ovo1", 1, BigDecimal.TEN);
        var itemsDtoMeat = new ItemsDto("3", "carne1", 1, BigDecimal.TEN);

        items.add(itemsDtoRice);
        items.add(itemsDtoEgg);
        items.add(itemsDtoMeat);

        var itemsDtoPasta= new ItemsDto("4", "massa1", 1, BigDecimal.TEN);
        var itemsDtoCheese = new ItemsDto("5", "queijo", 1, BigDecimal.TEN);

        itemsPasta.add(itemsDtoPasta);
        itemsPasta.add(itemsDtoCheese);

        var dish = DishEntity.builder().id("alaminuta1").dishName("Alaminuta").items(items).build();
        var dishPasta = DishEntity.builder().id("massa1").dishName("Massa").items(itemsPasta).build();

        dishs.add(dish);
        dishs.add(dishPasta);

        allDish.forEach(allD -> dishs.removeIf(d -> d.getDishName().equals(allD.getDishName())));

        dishRepository.saveAll(dishs);
    }

    @Bean
    public void initMongoStock() {

        List<StockEntity> allStock = stockRepository.findAll();

        var stocks = new ArrayList<StockEntity>();

        var stockCoca = StockEntity.builder().idIngredient("coca1").amount(3).build();
        var stockBean = StockEntity.builder().idIngredient("feijao1").amount(2).build();
        var stockRice = StockEntity.builder().idIngredient("arroz1").amount(3).build();
        var stockEgg = StockEntity.builder().idIngredient("ovo1").amount(2).build();
        var stockMeat = StockEntity.builder().idIngredient("carne1").amount(3).build();
        var stockPasta = StockEntity.builder().idIngredient("massa1").amount(2).build();
        var stockCheese = StockEntity.builder().idIngredient("queijo1").amount(3).build();

        stocks.add(stockCoca);
        stocks.add(stockBean);
        stocks.add(stockRice);
        stocks.add(stockEgg);
        stocks.add(stockMeat);
        stocks.add(stockPasta);
        stocks.add(stockCheese);

        allStock.forEach(allS -> stocks.removeIf(s -> s.getIdIngredient().equals(allS.getIdIngredient())));

        stockRepository.saveAll(stocks);
    }

    @Bean
    public void initIngredient() {

        List<IngredientEntity> allingredient = ingredientRepository.findAll();

        var ingredients = new ArrayList<IngredientEntity>();

        var stockCoca = IngredientEntity.builder().id("coca1").ingredientName("Coca").build();
        var stockBean = IngredientEntity.builder().id("feijao1").ingredientName("Feijão").build();
        var stockRice = IngredientEntity.builder().id("arroz1").ingredientName("Arroz").build();
        var stockEgg = IngredientEntity.builder().id("ovo1").ingredientName("Ovo").build();
        var stockMeat = IngredientEntity.builder().id("carne1").ingredientName("Carne").build();
        var stockPasta = IngredientEntity.builder().id("massa1").ingredientName("Massa").build();
        var stockCheese = IngredientEntity.builder().id("queijo1").ingredientName("Queijo").build();

        ingredients.add(stockCoca);
        ingredients.add(stockBean);
        ingredients.add(stockRice);
        ingredients.add(stockEgg);
        ingredients.add(stockMeat);
        ingredients.add(stockPasta);
        ingredients.add(stockCheese);

        allingredient.forEach(allI -> ingredients.removeIf(i -> i.getId().equals(allI.getId())));

        ingredientRepository.saveAll(ingredients);
    }
}