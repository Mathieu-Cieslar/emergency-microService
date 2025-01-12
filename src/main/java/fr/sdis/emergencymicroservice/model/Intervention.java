package fr.sdis.emergencymicroservice.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Intervention {
    private int id;
    private Camion camion;
    private Feu feu;
    private Caserne caserne;
    private List<Double[]> trajet;
    private Integer tempsTrajet;
}
