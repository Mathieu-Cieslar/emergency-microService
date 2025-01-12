package fr.sdis.emergencymicroservice;

import fr.sdis.emergencymicroservice.client.InterventionClient;
import fr.sdis.emergencymicroservice.model.Camion;
import fr.sdis.emergencymicroservice.model.Caserne;
import fr.sdis.emergencymicroservice.model.Feu;
import fr.sdis.emergencymicroservice.model.Intervention;
import fr.sdis.emergencymicroservice.service.CaserneService;
import fr.sdis.emergencymicroservice.service.FeuService;
import fr.sdis.emergencymicroservice.client.InterventionClient;
import fr.sdis.emergencymicroservice.service.InterventionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmergencyMicroServiceApplication {


	public static void main(String[] args) {
		var context = SpringApplication.run(EmergencyMicroServiceApplication.class, args);
		InterventionService interventionService = context.getBean(InterventionService.class);
		interventionService.CreateInterventionByCaptors();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
