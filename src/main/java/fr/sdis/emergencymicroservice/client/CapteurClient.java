package fr.sdis.emergencymicroservice.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sdis.emergencymicroservice.model.Capteur;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CapteurClient {
    @Value("${api.url}/capteur")
    private String apiUrl;

    private final RestTemplate restTemplate;
    public CapteurClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ObjectMapper objectMapper = new ObjectMapper();


    public List<Capteur> getCapteurs() {
        String reponse = null;
        try{
            reponse = restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des capteurs", e);
        }

        try {
            // Désérialisation
            return objectMapper.readValue(reponse, new TypeReference<List<Capteur>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }
    }

    public void setCapteurs(List<Capteur> capteurs) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(capteurs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }

        try{
            restTemplate.put(apiUrl, json);
            System.out.println("Update success");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour des capteurs", e);
        }
    }
}
