package com.restaurante.cozinhaservice.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CallOrderClientTest {

    @InjectMocks
    private CallOrderClient callOrderClient;
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDish() {

        callOrderClient.callOrder("1", "PRONTO");

        Mockito.verify(restTemplate, Mockito.times(1)).patchForObject(anyString(), anyString(), any());
    }
}