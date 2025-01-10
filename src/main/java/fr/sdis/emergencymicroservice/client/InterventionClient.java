package fr.sdis.emergencymicroservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sdis.emergencymicroservice.model.Feu;
import fr.sdis.emergencymicroservice.model.Intervention;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class InterventionClient {
    @Value("${api.url}/intervention")
    private String apiUrl;


    private final RestTemplate restTemplate;
    public InterventionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Intervention> getInterventions() {
        String reponse = null;
        try{
            reponse = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(reponse);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des interventions", e);
        }

        try {
            // Désérialisation
            return objectMapper.readValue(reponse, new TypeReference<List<Intervention>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }
    }

    public void createIntervention(Intervention intervention) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(intervention);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }

        try{
            restTemplate.postForObject(apiUrl, json, String.class);
            System.out.println("Post success");

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création d'un intervention", e);
        }
    }
}
