package com.restaurante.cozinhaservice.repository;

import com.restaurante.cozinhaservice.entity.IngredientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRepository extends MongoRepository<IngredientEntity, String> {

}
