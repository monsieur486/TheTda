package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.exeption.TdaException;
import com.mr486.tdacore.persistance.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameManagement {

    private final ReunionService reunionService;
    private final AmiService amiService;
    private final JoueurService joueurService;
    private final PartieService partieService;

    public void creation(List<Integer> amiIds) {

        UUID reunionUuid = reunionService.getReunionActiveUuid();
        if (reunionUuid == null) {
            throw new TdaException("La reunion n'existe pas !!!");
        }

        Integer statusReunion = reunionService.reunionActiveStatus();

        if (statusReunion > 1) {
            throw new TdaException("La reunion est cours !!!");
        }

        Set<Integer> amiIdsSet = new HashSet<>(amiIds);

        //vérification du nombre minimum de joueurs
        if (amiIdsSet.size() < 4) {
            throw new TdaException("Il faut au moins 4 joueurs pour commencer une partie !!!");
        }

        //vérification du nombre maximum de joueurs
        if (amiIdsSet.size() > 6) {
            throw new TdaException("Il faut au maximum 6 joueurs pour commencer une partie !!!");
        }

        //vérification de l'existence des amis
        for (Integer amiId : amiIdsSet) {
            if (!amiService.existeAmi(amiId)) {
                throw new TdaException("Ami avec id " + amiId + " n'existe pas !!!");
            }
        }

        //création des joueurs
        for (Integer amiId : amiIdsSet) {
            joueurService.createJoueur(amiId);
        }

        reunionService.setReunionActiveStatus(2);
    }

    public void raz() {
        int status = reunionService.reunionActiveStatus();
        if (status == 2) {
            throw new TdaException("La reunion est en cours !!!");
        }
        joueurService.supprimeJoueurs();
        partieService.deleteParties();
        reunionService.setReunionActiveStatus(1);
    }

    public void cagnotte() {
        int status = reunionService.reunionActiveStatus();
        if (status == 1) {
            throw new TdaException("La reunion n'a pas démarée !!!");
        }
        reunionService.setReunionActiveStatus(3);
    }

    public void addPartie(PartieForm partieForm) {
        int status = reunionService.reunionActiveStatus();
        if (status != 2) {
            throw new TdaException("La reunion n'est pas en cours !!!");
        }
        partieService.addPartie(partieForm);
    }

    public void updatePartie(int numPartie, PartieForm partieForm) {
        partieService.updatePartie(numPartie, partieForm);
    }

    public PartieForm getPartieFormByNumPartie(int numPartie) {
        Partie partie = partieService.getPartie(numPartie);
        PartieForm partieFormMapper = new PartieForm();
        partieFormMapper.setContratId(partie.getContratId());
        partieFormMapper.setPreneurId(partie.getPreneurId());
        partieFormMapper.setAppelId(partie.getAppelId());
        partieFormMapper.setMortId(partie.getMortId());
        partieFormMapper.setPetitAuBoutId(partie.getPetitAuBoutId());
        partieFormMapper.setEstFait(partie.getEstFait());
        partieFormMapper.setScore(partie.getScore());
        partieFormMapper.setChelem(partie.getChelem());
        partieFormMapper.setCapot(partie.getCapot());
        return partieFormMapper;
    }

}
