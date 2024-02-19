package com.restaurante.cozinhaservice.client;

import com.restaurante.cozinhaservice.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class CallOrderClient {

    private RestTemplate restTemplate;
    private static String ORDER_BASE_URL = "https://pedido-service.onrender.com:443/order/";

    public void callOrder(String id ,String status) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);

        restTemplate.setRequestFactory(requestFactory);
        restTemplate.patchForObject(ORDER_BASE_URL + id, status, OrderDto.class);
    }
}
