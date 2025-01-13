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
import java.util.Objects;

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


        List<Feu> reponse = feuClient.getFeuxIsActif();

        List<Feu> feu = feuService.createFeuByCaptors(); //sup
        Feu feuBDD = null;

        for(Feu feuCreated : feu) {
            System.out.println("Feu : " + feu);
            if (feu == null) {
                break;
            }

            boolean alreadyExist = false;

            for (Feu feuActif : reponse) {
                //log de la condition suivante
                System.out.println("Feu actif : " + feuActif);
                System.out.println("Feu créé : " + feuCreated);
                if (Objects.equals(feuActif.getCoorX(), feuCreated.getCoorX()) && Objects.equals(feuActif.getCoorY(), feuCreated.getCoorY())) {
                    alreadyExist = true;
                    System.out.println("Feu déjà existaaaaaaaaaaaaaaant");
                    break;
                }
            }
            if(!alreadyExist){
                feuBDD = feuClient.createFeu(feuCreated);

                Caserne caserne = caserneService.getCaserneProche(feuCreated.getCoorX(), feuCreated.getCoorY()); //sup
                System.out.println("Caserne la plus proche : " + caserne);
                Camion camion = new Camion();

                List<Double[]> trajet = trajetClient.getTrajet(caserne.getCoorX(), caserne.getCoorY(), feuCreated.getCoorX(), feuCreated.getCoorY());

                //creer une intervention
                Intervention intervention = new Intervention(0, camion, feuBDD, caserne, trajet, 30000, null);
                System.out.println("Intervention : " + intervention);

                interventionClient.createIntervention(intervention);
            }
        }
    }
}