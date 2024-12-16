package fr.sdis.emergencymicroservice.service;

import fr.sdis.emergencymicroservice.client.CapteurClient;
import fr.sdis.emergencymicroservice.client.FeuClient;
import fr.sdis.emergencymicroservice.model.Capteur;
import fr.sdis.emergencymicroservice.model.Feu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeuService {

    private final FeuClient feuClient;
    private final CapteurClient capteurClient;

    public FeuService(FeuClient feuClient, CapteurClient capteurClient) {
        this.feuClient = feuClient;
        this.capteurClient = capteurClient;
    }

    public void createFeuByCaptors() {
        List<Capteur> capteurs = capteurClient.getCapteurs();
        capteurs.sort((c1, c2) -> Double.compare(c2.getValeur(), c1.getValeur()));
        //trie de ma liste capteurs en fonction de l'intensite
        System.out.println(capteurs);
        //on prend les 3 premiers capteurs pour faire une triangulation
        double[] coordonnees = triangulate(capteurs.get(0).getCoorX(), capteurs.get(0).getCoorY(), getDistanceByIntensite(capteurs.get(0).getValeur()),
                capteurs.get(1).getCoorX(), capteurs.get(1).getCoorY(), getDistanceByIntensite(capteurs.get(1).getValeur()),
                capteurs.get(2).getCoorX(), capteurs.get(2).getCoorY(), getDistanceByIntensite(capteurs.get(2).getValeur()));
        System.out.println("Coordonnées triangulées : " + coordonnees[0] + " " + coordonnees[1]);
        //Feu feu = new Feu(0,1.2,1.0,1,1);
        //feuClient.createFeu(feu);
    }

    // Fonction pour calculer la position d'un point en utilisant la trilatération
    public static double[] triangulate(double x1, double y1, double d1,
                                       double x2, double y2, double d2,
                                       double x3, double y3, double d3) {
        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double C = 2 * (x3 - x1);
        double D = 2 * (y3 - y1);

        double E = d1 * d1 - d2 * d2 - x1 * x1 + x2 * x2 - y1 * y1 + y2 * y2;
        double F = d1 * d1 - d3 * d3 - x1 * x1 + x3 * x3 - y1 * y1 + y3 * y3;

        // Calculer les coordonnées X et Y du point triangulé
        double y = (E - C * (F / D)) / (B - A * (D / C));
        double x = (E - B * y) / A;

        return new double[] {x, y};  // Retourne les coordonnées estimées
    }

    public int getDistanceByIntensite(int intensite){
        int distance = 0;

        if(intensite == 10){
            distance = 3;
        }else if(intensite == 9){
            distance = 4;
        }else if(intensite == 8){
            distance = 5;
        }else if(intensite == 7){
            distance = 6;
        }else if(intensite == 6){
            distance = 7;
        }else if(intensite == 5){
            distance = 8;
        }else if(intensite == 4){
            distance = 9;
        }else if(intensite == 3){
            distance = 10 ;
        }else if (intensite == 2){
            distance = 11 ;
        }else if (intensite == 1){
            distance = 12 ;
        }else {
            distance = 13;
        }
        return distance*1000;
    }

}
