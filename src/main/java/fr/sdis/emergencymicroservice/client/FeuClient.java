package fr.sdis.emergencymicroservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sdis.emergencymicroservice.model.Feu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FeuClient {
    @Value("${api.url}/feu")
    private String apiUrl;


    private final RestTemplate restTemplate;
    public FeuClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Feu> getFeux() {
        String reponse = null;
        try{
            reponse = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(reponse);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des feux", e);
        }

        try {
            // Désérialisation
            return objectMapper.readValue(reponse, new TypeReference<List<Feu>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }
    }

    public void createFeu(Feu feu) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(feu);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }

        try{
            restTemplate.postForObject(apiUrl, json, String.class);
            System.out.println("Post success");

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création d'un feu", e);
        }
    }
}
