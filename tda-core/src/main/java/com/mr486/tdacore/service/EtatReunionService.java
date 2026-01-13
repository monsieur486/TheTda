package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.EtatReunion;
import com.mr486.tdacore.exeption.TdaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtatReunionService {

    private final ReunionService reunionService;
    private final ResumeService resumeService;
    private final DetailPartieService detailPartieService;
    private final ScoreService scoreService;
    private final InfoGraphiqueService infoGraphiqueService;

    public EtatReunion getEtatServeur() {
        EtatReunion etatReunion = new EtatReunion();
        Integer statusReunionActive = reunionService.reunionActiveStatus();
        if (statusReunionActive == 0) {
            throw new TdaException("La reunion active est en erreur !!!");
        }
        etatReunion.setStatus(statusReunionActive);
        if (statusReunionActive == 1) {
            etatReunion.setResume("En attente de joueurs...");
        } else {
            etatReunion.setResume(resumeService.createResume());
        }
        etatReunion.setScores(scoreService.getScores());
        etatReunion.setParties(detailPartieService.getDetails());
        etatReunion.setLabels(infoGraphiqueService.getLabels());
        etatReunion.setDatasets(infoGraphiqueService.getDatasets());
        return etatReunion;
    }
}
