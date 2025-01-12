package fr.sdis.emergencymicroservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sdis.emergencymicroservice.model.Intervention;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;


import java.util.ArrayList;
import java.util.List;

@Component
public class TrajetClient {
    private String apiKey = "5b3ce3597851110001cf62485485dd442f4f4809a5fea27879963491";
    private String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=${"+apiKey+"}&start=${start[1]},${start[0]}&end=${end[1]},${end[0]}";


    private final RestTemplate restTemplate;
    public TrajetClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Double[]> getTrajet(Double feuCoorX, Double feuCoorY, Double caserneCoorX, Double caserneCoorY) {
        String reponse = null;
        try{
            apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key="+apiKey+"&start="+caserneCoorY+","+caserneCoorX+"&end="+feuCoorY+","+feuCoorX;
            System.out.println(apiUrl);
            reponse = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(reponse);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du trajet", e);
        }

        try {

            // Traiter la réponse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(reponse);

            // Récupérer les coordonnées dans la réponse JSON
            JsonNode coordinates = root
                    .path("features")
                    .get(0)
                    .path("geometry")
                    .path("coordinates");

            // Transformer les coordonnées [lon, lat] en [lat, lon]
            List<Double[]> transformedCoordinates = new ArrayList<>();
            for (JsonNode coord : coordinates) {
                double lat = coord.get(1).asDouble();
                double lon = coord.get(0).asDouble();
                transformedCoordinates.add(new Double[]{lat, lon});
            }

            return transformedCoordinates;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la conversion en JSON", e);
        }
    }
}
