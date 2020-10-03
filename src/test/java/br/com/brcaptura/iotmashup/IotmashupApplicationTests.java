package br.com.brcaptura.iotmashup;

import br.com.brcaptura.iotmashup.service.IotMashupService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class IotmashupApplicationTests {

	@Autowired
	IotMashupService iotMashupService;

	@Test
	void contextLoads() {
		assertThat(iotMashupService).isNotNull();
	}

}
