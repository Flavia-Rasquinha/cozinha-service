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
@Document(collection = "Estoque")
public class StockEntity {

    @Id
    private String id;
    @Field(name = "Quantidade")
    private int amount;
    @Field(name = "NomeProduto")
    private String productName;

}
