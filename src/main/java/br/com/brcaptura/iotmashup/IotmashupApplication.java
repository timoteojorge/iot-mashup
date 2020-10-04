package br.com.brcaptura.iotmashup;

import br.com.brcaptura.iotmashup.model.Mashup;
import br.com.brcaptura.iotmashup.service.IotMashupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class IotmashupApplication {

    Logger logger = LoggerFactory.getLogger(IotmashupApplication.class);

    @Autowired
    IotMashupService iotMashupService;

    public static void main(String[] args) {
        SpringApplication.run(IotmashupApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void buildMashupsOnStartup() {
        try {
            List<Mashup> mashupList = iotMashupService.buildMashups();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(mashupList);
            logger.info("Retornando todos os mashups encontrados: ");
            logger.info(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("Erro ao processar resposta de requisicao");
        }
    }


}
