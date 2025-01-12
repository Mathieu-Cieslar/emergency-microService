package fr.sdis.emergencymicroservice.service;
import fr.sdis.emergencymicroservice.client.FeuClient;
import fr.sdis.emergencymicroservice.client.InterventionClient;
import fr.sdis.emergencymicroservice.client.TrajetClient;
import fr.sdis.emergencymicroservice.model.Camion;
import fr.sdis.emergencymicroservice.model.Caserne;
import fr.sdis.emergencymicroservice.model.Feu;
import fr.sdis.emergencymicroservice.model.Intervention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterventionService {

    @Autowired
    InterventionClient interventionClient;

    @Autowired
    FeuClient feuClient;

    @Autowired
    TrajetClient trajetClient;

    @Autowired
    FeuService feuService;

    @Autowired
    CaserneService caserneService;

    public void CreateInterventionByCaptors() {

        Feu feu = feuService.createFeuByCaptors(); //sup
        System.out.println("Feu : " + feu);

        Feu feuBDD = feuClient.createFeu(feu);

        Caserne caserne = caserneService.getCaserneProche(feu.getCoorX(), feu.getCoorY()); //sup
        System.out.println("Caserne la plus proche : " + caserne);
        Camion camion = new Camion();

        List<Double[]> trajet = trajetClient.getTrajet(caserne.getCoorX(), caserne.getCoorY(), feu.getCoorX(), feu.getCoorY());

        for (Double[] coord : trajet) {
            System.out.println("Lat: " + coord[0] + ", Lon: " + coord[1]);
        }

        //creer une intervention
        Intervention intervention = new Intervention(0,camion, feuBDD, caserne, trajet, 30000); //sup
        System.out.println("Intervention : " + intervention);

        interventionClient.createIntervention(intervention);
    }
}