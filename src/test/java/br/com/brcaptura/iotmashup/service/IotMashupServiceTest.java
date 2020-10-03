package br.com.brcaptura.iotmashup.service;

import br.com.brcaptura.iotmashup.model.Mashup;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class IotMashupServiceTest {

    @Autowired
    IotMashupService iotMashupService;

    @Test
    public void buildMashups(){
        List<Mashup> mashups = iotMashupService.buildMashups();
        assert mashups.size() > 0;
    }
}
