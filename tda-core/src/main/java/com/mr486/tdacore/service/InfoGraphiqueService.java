package com.mr486.tdacore.service;

import com.mr486.tdacore.configuration.ApplicationConfiguration;
import com.mr486.tdacore.dto.Dataset;
import com.mr486.tdacore.dto.JoueurListe;
import com.mr486.tdacore.persistance.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoGraphiqueService {

    private final JoueurService joueurService;
    private final PartieService partieService;
    private final ToolScoreService toolScoreService;

    public List<Integer> getLabels(){
        int nbParties = partieService.getNbPartie();
        List<Integer> labels = new ArrayList<>();
        labels.add(0);
        for(int i = 1; i <= nbParties; i++){
            labels.add(i);
        }
        return labels;
    }

    public List<Dataset> getDatasets(){
        List<Partie> parties = partieService.getAllParties();
        List<JoueurListe> joueurs = joueurService.getJoueursInscrits();
        List<Dataset> datasets = new ArrayList<>();
        for(JoueurListe joueur : joueurs){
            Dataset dataset = new Dataset();
            dataset.setLabel(joueur.getNom());
            dataset.setData(getDatas(joueurs.size(), parties, joueur.getId()));
            dataset.setBorderwith(ApplicationConfiguration.BORDER_WIDTH);
            datasets.add(dataset);
        }

        return datasets;

    }

    private List<Integer> getDatas(int nbJoueurs, List<Partie> parties, int joueurId){
        List<Integer> datas = new ArrayList<>();
        int points = 0;
        List<Integer> deltas = toolScoreService.listeDelta(nbJoueurs, parties, joueurId);
        for(int delta : deltas){
            points += delta;
            datas.add(points);
        }
        return datas;
    }
}
