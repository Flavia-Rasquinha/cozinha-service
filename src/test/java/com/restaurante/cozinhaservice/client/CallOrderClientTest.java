package com.restaurante.cozinhaservice.client;

import com.restaurante.cozinhaservice.enums.StatusEnum;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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
    public void createCallOrderWithValidIdShouldReturnSucess() {

        callOrderClient.callOrder("1", StatusEnum.READY);

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(
                Mockito.any(String.class), Mockito.any(HttpMethod.class), Mockito.any(), Mockito.any(Class.class));
    }
}