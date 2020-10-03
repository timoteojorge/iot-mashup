package br.com.brcaptura.iotmashup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IotmashupConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
