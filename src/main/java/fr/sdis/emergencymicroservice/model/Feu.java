package fr.sdis.emergencymicroservice.model;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Feu {
    private int id;
    private Double coorX;
    private Double coorY;
    private int intensite;
    private int statusIntervention;
}
