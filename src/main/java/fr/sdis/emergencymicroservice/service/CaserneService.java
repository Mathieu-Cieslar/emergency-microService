package fr.sdis.emergencymicroservice.service;

import fr.sdis.emergencymicroservice.client.CaserneClient;
import fr.sdis.emergencymicroservice.model.Caserne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaserneService {

    @Autowired
    CaserneClient caserneClient;

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515 * 1.609344;
            return (dist);
        }
    }

    public Caserne getCaserneProche(double coorX, double coorY) {
        List<Caserne> casernes = caserneClient.getCasernes();
        System.out.println("CaserneService: getCaserneProche: casernes: " + casernes);
        // Caserne la plus proche
        Caserne caserneProche = null;
        for (Caserne caserne : casernes) {
            if (caserneProche == null) {
                caserneProche = caserne;
            } else {
                if (distance(coorX, coorY, caserne.getCoorX(), caserne.getCoorY()) < distance(coorX, coorY, caserneProche.getCoorX(), caserneProche.getCoorY())) {
                    //if(caserne.getNbCamion() > 0){
                        caserneProche = caserne;
                    //}
                }
            }
        }
        return caserneProche;
    }
}
