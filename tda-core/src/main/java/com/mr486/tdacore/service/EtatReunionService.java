package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.EtatReunion;
import com.mr486.tdacore.exeption.TdaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtatReunionService {

    private final ReunionService reunionService;

    public EtatReunion getEtatServeur() {
        EtatReunion etatReunion = new EtatReunion();
        Integer statusReunionActive = reunionService.reunionActiveStatus();
        if(statusReunionActive==0){
            throw new TdaException("La reunion active est en erreur !!!");
        }
        etatReunion.setStatus(statusReunionActive);
        return etatReunion;
    }
}
