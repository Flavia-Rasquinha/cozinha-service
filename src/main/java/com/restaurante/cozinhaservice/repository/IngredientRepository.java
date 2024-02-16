package com.restaurante.cozinhaservice.repository;

import com.restaurante.cozinhaservice.entity.IngredientEntity;
import com.restaurante.cozinhaservice.entity.StockEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IngredientRepository extends MongoRepository<IngredientEntity, String> {

}
