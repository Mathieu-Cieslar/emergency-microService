package fr.sdis.emergencymicroservice.model;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Caserne {
    private int id;
    private double coorX;
    private double coorY;
    private Integer nbCamion;
    private Integer nbPompier;
    private String nom;
    private List<Intervention> interventions;
}
