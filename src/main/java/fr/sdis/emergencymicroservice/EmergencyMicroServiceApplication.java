package fr.sdis.emergencymicroservice;

import fr.sdis.emergencymicroservice.model.Feu;
import fr.sdis.emergencymicroservice.service.CaserneService;
import fr.sdis.emergencymicroservice.service.FeuService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmergencyMicroServiceApplication {


	public static void main(String[] args) {
		var context = SpringApplication.run(EmergencyMicroServiceApplication.class, args);
		FeuService feuService = context.getBean(FeuService.class);
		CaserneService caserneService = context.getBean(CaserneService.class);
		Feu feu = feuService.createFeuByCaptors();
		System.out.println("Feu : " + feu);
		caserneService.getCaserneProche(feu.getCoorX(), feu.getCoorY());
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
