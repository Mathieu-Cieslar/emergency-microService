package fr.sdis.emergencymicroservice.model;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Caserne {
    private int idCamion;
    private int idIntervention;
    private int tempsIntervention;
}
