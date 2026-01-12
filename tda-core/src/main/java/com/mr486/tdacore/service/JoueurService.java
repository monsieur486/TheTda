package com.mr486.tdacore.service;

import com.mr486.tdacore.configuration.ApplicationConfiguration;
import com.mr486.tdacore.dto.JoueurListe;
import com.mr486.tdacore.persistance.Ami;
import com.mr486.tdacore.persistance.Joueur;
import com.mr486.tdacore.repository.JoueurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoueurService {

    private final AmiService amiService;
    private final JoueurRepository joueurRepository;

    public void createJoueur(Integer amiId) {
        Joueur joueur = Joueur.builder()
                .amiId(amiId)
                .build();
        joueurRepository.save(joueur);
    }

    public void supprimeJoueurs() {
        joueurRepository.deleteAll();
    }

    public Integer getNbJoueur(){
        return joueurRepository.findAll().size();
    }

    public List<JoueurListe> getJoueursInscrits(){
        List<JoueurListe> joueurs = new ArrayList<>();
        List<Joueur> joueursDB = joueurRepository.findAll();
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
        //Deque<JoueurListe> deque = new ArrayDeque<>(joueurs);
        //deque.addFirst(new JoueurListe(0, "A définir"));
        //joueurs = new ArrayList<>(deque);
        return joueurs;
    }

    public Boolean existeJoueur(Integer id) {
        if (id == 0) {
            return true;
        }
        return joueurRepository.existsByAmiId(id);
    }
}
