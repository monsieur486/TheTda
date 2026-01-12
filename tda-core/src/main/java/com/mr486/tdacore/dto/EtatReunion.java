package com.mr486.tdacore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@Builder
public class EtatReunion {
    private Integer status;
    private String resume;
    private List<PointJoueur> scores;
    private List<String> parties;
    private InfoGraphique infoGraphique;

    public EtatReunion() {
        this.status = 0;
        this.resume = "";
        this.scores = new java.util.ArrayList<>();
        this.parties = new java.util.ArrayList<>();
        this.infoGraphique = new InfoGraphique();
    }
}
