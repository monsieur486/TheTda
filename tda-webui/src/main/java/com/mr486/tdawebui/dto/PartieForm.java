package com.mr486.tdawebui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartieForm {
    private Integer contratId = 0;
    private Integer preneurId = 0;
    private Integer appelId = 0;
    private Integer mortId = 0;
    private Boolean estFait = false;
    private Integer score = 0;
    private Integer petitAuBoutId = 0;
    private Boolean chelem = false;
    private Boolean capot = false;
}
