package com.mr486.tdacore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointJoueur {
    private String nom;
    private String color;
    private Integer score;
    private String avatar;
}
