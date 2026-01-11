package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.EtatReunion;
import org.springframework.stereotype.Service;

@Service
public class EtatReunionService {

    public EtatReunion getEtatServeur() {
        return new EtatReunion();
    }
}
