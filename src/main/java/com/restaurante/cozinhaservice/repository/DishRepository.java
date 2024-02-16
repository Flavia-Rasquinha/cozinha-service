package com.restaurante.cozinhaservice.repository;

import com.restaurante.cozinhaservice.entity.DishEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishRepository extends MongoRepository<DishEntity, String> {
}
