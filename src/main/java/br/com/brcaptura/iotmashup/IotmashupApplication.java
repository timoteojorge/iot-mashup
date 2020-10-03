package br.com.brcaptura.iotmashup;

import br.com.brcaptura.iotmashup.model.Mashup;
import br.com.brcaptura.iotmashup.service.IotMashupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class IotmashupApplication {

    @Autowired
    IotMashupService iotMashupService;

    public static void main(String[] args) {
        SpringApplication.run(IotmashupApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void buildMashupsOnStartup() {
        try {
            List<Mashup> mashupList = iotMashupService.search();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(mashupList);
            System.out.println("Retornando todos os mashups encontrados: ");
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
