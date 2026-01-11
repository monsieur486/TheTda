package com.mr486.tdacore.service;

import com.mr486.tdacore.configuration.ApplicationConfiguration;
import com.mr486.tdacore.dto.JoueurListe;
import com.mr486.tdacore.exeption.TdaException;
import com.mr486.tdacore.persistance.Ami;
import com.mr486.tdacore.persistance.Joueur;
import com.mr486.tdacore.persistance.Reunion;
import com.mr486.tdacore.repository.JoueurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoueurService {
    private final ReunionService reunionService;
    private final AmiService amiService;
    private final JoueurRepository joueurRepository;

    private void createJoueur(Integer amiId, UUID reunionUuid) {
        Joueur joueur = Joueur.builder()
                .amiId(amiId)
                .reunionUuid(reunionUuid)
                .build();
        log.warn("Création du joueur avec amiId {} et reunionUuid {}", amiId, reunionUuid);
        joueurRepository.save(joueur);
    }

    private void supprimeJoueurs(){
        joueurRepository.deleteAll();
    }

    public void createPartie(List<Integer> amiIds) {

        UUID reunionUuid = reunionService.getReunionActiveUuid();
        if(reunionUuid==null){
            throw new TdaException("La reunion n'existe pas !!!");
        }
        log.warn("Reunion UUID : {}", reunionUuid);

        Integer statusReunion = reunionService.reunionActiveStatus();

        if(statusReunion>1){
            throw new TdaException("La reunion est cours !!!");
        }

        Set<Integer> amiIdsSet = new HashSet<>(amiIds);

        //vérification du nombre minimum de joueurs
        if(amiIdsSet.size() <4){
            throw new TdaException("Il faut au moins 4 joueurs pour commencer une partie !!!");
        }

        //vérification du nombre maximum de joueurs
        if(amiIdsSet.size()>6){
            throw new TdaException("Il faut au maximum 6 joueurs pour commencer une partie !!!");
        }

        //vérification de l'existence des amis
        for(Integer amiId : amiIdsSet){
            if(!amiService.existeAmi(amiId)){
                throw new TdaException("Ami avec id " + amiId + " n'existe pas !!!");
            };
        }

        //suppression des joueurs existants
        supprimeJoueurs();

        //création des joueurs
        for(Integer amiId : amiIdsSet){
            log.warn("Création du joueur avec amiId : {} et avec UUID {}", amiId, reunionUuid);
            createJoueur(amiId, reunionUuid);
        }

        reunionService.setReunionActiveStatus(2);
    }

    public Integer getNbJoueur(){
        Reunion reunion = reunionService.getReunionActive();
         return joueurRepository.findAllByReunionUuid(reunion.getUuid()).size();
    }

    public List<JoueurListe> getJoueursInscrits(){
        List<JoueurListe> joueurs = new ArrayList<>();
        Reunion reunion = reunionService.getReunionActive();
        List<Joueur> joueursDB = joueurRepository.findAllByReunionUuid(reunion.getUuid());
        for(Joueur joueur : joueursDB){
            Ami ami = amiService.getAmiById(joueur.getAmiId());
            Integer id = ami.getId();
            String nom = ami.getNom();
            if(ami.getIsGuest()){
                nom += ApplicationConfiguration.IMAGE_GUEST;
            }
            joueurs.add(new JoueurListe(id, nom));
        }
        joueurs.sort(Comparator.comparing(JoueurListe::getNom));
        return joueurs;
    }
}
