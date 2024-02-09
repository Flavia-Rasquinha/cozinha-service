package com.restaurante.cozinhaservice.repository;

import com.restaurante.cozinhaservice.entity.DishEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DishRepository extends MongoRepository<DishEntity, String> {
    Optional<DishEntity> findByDishName(String name);
}
