package fr.sdis.emergencymicroservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sdis.emergencymicroservice.model.Caserne;
import fr.sdis.emergencymicroservice.model.Intervention;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component

public class CaserneClient {
    @Value("${api.url}/caserne")
    private String apiUrl;


    private final RestTemplate restTemplate;
    public CaserneClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Caserne> getCasernes() {
        String reponse = null;
        try{
            reponse = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(reponse);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des casernes", e);
        }

        try {
            // Désérialisation
            return objectMapper.readValue(reponse, new TypeReference<List<Caserne>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }
    }

    public void createCaserne(Caserne caserne) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(caserne);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }

        try{
            restTemplate.postForObject(apiUrl, json, String.class);
            System.out.println("Post success");

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création d'une caserne", e);
        }
    }
}
