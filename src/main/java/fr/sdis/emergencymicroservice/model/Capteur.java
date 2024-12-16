package fr.sdis.emergencymicroservice.model;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Capteur {
    private String id;
    private Double coorX;
    private Double coorY;
    private String typeCapteur;
    private Integer valeur;
}