package fr.sdis.emergencymicroservice.model;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Feu {
    private int id;
    private Double coorX;
    private Double coorY;
    private int intensite;
    private List<Intervention> Interventions;
}
