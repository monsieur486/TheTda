package com.mr486.tdacore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrat {
    private Integer id;
    private String nom;
    private String initiale;
    private Integer points;
}
