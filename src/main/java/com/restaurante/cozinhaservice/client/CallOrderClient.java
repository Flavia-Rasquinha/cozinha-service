package com.restaurante.cozinhaservice.client;

import com.restaurante.cozinhaservice.dto.OrderDto;
import com.restaurante.cozinhaservice.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CallOrderClient {

    private final Logger logger = LoggerFactory.getLogger(CallOrderClient.class);
    private final RestTemplate restTemplate;
    @Value("${order.url}")
    private String orderBaseUrl;

    public void callOrder(String id , StatusEnum status) {
        logger.info("Calling order update idPedido: {}, processing for status: {}", id, status);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StatusEnum> requestEntity = new HttpEntity<>(status, headers);

        restTemplate.exchange(orderBaseUrl + id, HttpMethod.PATCH, requestEntity, OrderDto.class);
    }
}
