package com.restaurante.cozinhaservice.client;

import com.restaurante.cozinhaservice.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class CallOrderClient {

    private RestTemplate restTemplate;
    private static String ORDER_BASE_URL = "http://localhost:8080/order/";
    public void callOrder(OrderDto orderDto) {
        restTemplate.put(ORDER_BASE_URL + orderDto.id(), orderDto);
    }
}
