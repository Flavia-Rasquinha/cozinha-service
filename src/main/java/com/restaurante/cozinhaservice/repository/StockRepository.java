package com.restaurante.cozinhaservice.repository;

import com.restaurante.cozinhaservice.entity.StockEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StockRepository extends MongoRepository<StockEntity, String> {
    Optional<StockEntity> findByProductName(String name);

}
