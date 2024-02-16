package com.restaurante.cozinhaservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "id")
@Document(collection = "Ingrediente")
public class IngredientEntity {

    @Id
    private String id;
    @Field(name = "NomeIngrediente")
    private String ingredientName;

}
