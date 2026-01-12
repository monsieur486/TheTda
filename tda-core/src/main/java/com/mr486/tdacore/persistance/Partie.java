package com.mr486.tdacore.persistance;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "parties")
public class Partie {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private Integer numeroPartie;
    private Integer contratId;
    private Integer preneurId;
    private Integer appelId;
    private Integer mortId;
    private Boolean estFait;
    private Integer score;
    private Integer petitAuBoutId;
    private Boolean chelem;
    private Boolean capot;
}
