package fr.sdis.emergencymicroservice.service;

import fr.sdis.emergencymicroservice.client.CapteurClient;
import fr.sdis.emergencymicroservice.client.FeuClient;
import fr.sdis.emergencymicroservice.model.Capteur;
import fr.sdis.emergencymicroservice.model.Feu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeuService {

    @Autowired
    FeuClient feuClient;
    @Autowired
    CapteurClient capteurClient;

    // Constante pour la conversion des degrés en radians
    private static final double DEG_TO_RAD = Math.PI / 180.0;

    public Feu createFeuByCaptors() {
        List<Capteur> capteurs = capteurClient.getCapteurs();
        capteurs.sort((c1, c2) -> Double.compare(c2.getValeur(), c1.getValeur()));
        //trie de ma liste capteurs en fonction de l'intensite
        System.out.println(capteurs);
        //on prend les 3 premiers capteurs pour faire une triangulation
        double[] coordonnees = triangulate(capteurs.get(0).getCoorX(), capteurs.get(0).getCoorY(), getDistanceByIntensite(capteurs.get(0).getValeur()),
                capteurs.get(1).getCoorX(), capteurs.get(1).getCoorY(), getDistanceByIntensite(capteurs.get(1).getValeur()),
                capteurs.get(2).getCoorX(), capteurs.get(2).getCoorY(), getDistanceByIntensite(capteurs.get(2).getValeur()));
        System.out.println("Coordonnées triangulées : " + coordonnees[0] + " " + coordonnees[1]);

        // Création d'un feu avec les coordonnées triangulées et l'intensité moyenne des 3 capteurs
        return new Feu(0, coordonnees[0], coordonnees[1], 10, null);

    }

    // Fonction pour calculer la position d'un point en utilisant la trilatération
    public static double[] triangulate(double x1, double y1, double r1,
                                       double x2, double y2, double r2,
                                       double x3, double y3, double r3) {
        //affiche tous les paramètres
        System.out.println("x1 : " + x1 + " y1 : " + y1 + " r1 : " + r1);
        System.out.println("x2 : " + x2 + " y2 : " + y2 + " r2 : " + r2);
        System.out.println("x3 : " + x3 + " y3 : " + y3 + " r3 : " + r3);
        // Définition des variables auxiliaires pour simplifier les calculs
        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double D = 2 * (x3 - x1);
        double E = 2 * (y3 - y1);

        double C = Math.pow(r1, 2) - Math.pow(r2, 2) - Math.pow(x1, 2) + Math.pow(x2, 2) - Math.pow(y1, 2) + Math.pow(y2, 2);
        double F = Math.pow(r1, 2) - Math.pow(r3, 2) - Math.pow(x1, 2) + Math.pow(x3, 2) - Math.pow(y1, 2) + Math.pow(y3, 2);

        // Calcul des coordonnées du point P(x, y)
        double x = (C * E - F * B) / (A * E - D * B);
        double y = (C * D - A * F) / (B * D - A * E);

        return new double[]{x, y};
    }

    // Fonction pour calculer la distance entre deux points (en mètres) en utilisant la formule Haversine
    private static double haversine(double lat1, double lon1, double lat2, double lon2) {
        // Rayon de la Terre en mètres
        double R = 6371000;

        // Convertir les degrés en radians
        lat1 = lat1 * DEG_TO_RAD;
        lon1 = lon1 * DEG_TO_RAD;
        lat2 = lat2 * DEG_TO_RAD;
        lon2 = lon2 * DEG_TO_RAD;

        // Calculer la différence entre les latitudes et longitudes
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        // Formule de Haversine
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Retourner la distance en mètres
        return R * c;
    }


    public int getDistanceByIntensite(int intensite){
        int distance = 0;
        //afficher l'intensité
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
