package com.restaurante.cozinhaservice.entity;

import com.restaurante.cozinhaservice.dto.ItemsDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = "id")
@Document(collection = "Pratos")
public class DishEntity {

    @Id
    private String id;
    @Field(name = "NomePrato")
    private String dishName;
    @Field(name = "Items")
    private List<ItemsDto> items;

}
