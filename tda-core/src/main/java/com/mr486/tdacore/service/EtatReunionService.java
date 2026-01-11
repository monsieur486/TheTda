package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.EtatReunion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtatReunionService {

    private final ReunionService reunionService;

    public EtatReunion getEtatServeur() {
        EtatReunion etat = new EtatReunion();
        etat.setStatus(reunionService.reunionActiveStatus());
        return etat;
    }
}
