package fr.sdis.emergencymicroservice.model;

import lombok.*;

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
}
