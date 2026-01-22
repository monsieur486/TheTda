package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.exeption.TdaException;
import com.mr486.tdacore.persistance.Partie;
import com.mr486.tdacore.repository.PartieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartieService {

    private final PartieRepository partieRepository;
    private final JoueurService joueurService;

    public Partie getPartie(int numPartie) {
        return partieRepository.findPartieByNumeroPartie(numPartie).orElseThrow(() -> new TdaException("La partie n'existe pas !!!"));
    }

    public void deleteParties() {
        partieRepository.deleteAll();
    }

    public void addPartie(PartieForm partieForm) {
        Partie partie = new Partie();
        int nombrePartie = partieRepository.findAll().size();
        partie = Partie.builder()
                .numeroPartie(nombrePartie + 1)
                .contratId(partieForm.getContratId())
                .preneurId(partieForm.getPreneurId())
                .appelId(partieForm.getAppelId())
                .mortId(partieForm.getMortId())
                .estFait(partieForm.getEstFait())
                .score(partieForm.getScore())
                .petitAuBoutId(partieForm.getPetitAuBoutId())
                .chelem(partieForm.getChelem())
                .capot(partieForm.getCapot())
                .build();
        verification(partieForm);
        partieRepository.save(partie);
    }

    public void updatePartie(int numPartie, PartieForm partieForm) {
        Partie partie = partieRepository.findPartieByNumeroPartie(numPartie).orElseThrow(() -> new TdaException("La partie n'existe pas !!!"));
        partie.setContratId(partieForm.getContratId());
        partie.setPreneurId(partieForm.getPreneurId());
        partie.setAppelId(partieForm.getAppelId());
        partie.setMortId(partieForm.getMortId());
        partie.setEstFait(partieForm.getEstFait());
        partie.setScore(partieForm.getScore());
        partie.setPetitAuBoutId(partieForm.getPetitAuBoutId());
        partie.setChelem(partieForm.getChelem());
        partie.setCapot(partieForm.getCapot());
        verification(partieForm);
        partieRepository.save(partie);
    }

    public List<Partie> getAllParties() {
        return partieRepository.findAllByOrderByNumeroPartieAsc();
    }

    public Integer getNbPartie() {
        return partieRepository.findAll().size();
    }

    private void verification(PartieForm partieForm) {

        int nombreJoueur = joueurService.getNbJoueur();

        if (partieForm.getContratId() > 7 || partieForm.getContratId() < 1) {
            throw new TdaException("Contrat invalide !!!");
        }

        if (!joueurService.existeJoueur(partieForm.getPreneurId())) {
            throw new TdaException("Preneur avec id " + partieForm.getPreneurId() + " n'existe pas !!!");
        }

        if (!joueurService.existeJoueur(partieForm.getAppelId())) {
            throw new TdaException("Appel avec id " + partieForm.getAppelId() + " n'existe pas !!!");
        }

        if (!joueurService.existeJoueur(partieForm.getMortId())) {
            throw new TdaException("Mort avec id " + partieForm.getMortId() + " n'existe pas !!!");
        }

        if (!joueurService.existeJoueur(partieForm.getPetitAuBoutId())) {
            throw new TdaException("Petit au nout avec id " + partieForm.getPetitAuBoutId() + " n'existe pas !!!");
        }

        if (partieForm.getContratId() == 1) {
            if (partieForm.getPreneurId() > 0) {
                throw new TdaException("Pas de preneur en cas de belge !!!");
            }
            if (partieForm.getAppelId() > 0) {
                throw new TdaException("Pas d'appel en cas de belge !!!");
            }
            if (partieForm.getPetitAuBoutId() > 0) {
                throw new TdaException("Pas de petit au bout en cas de belge !!!");
            }
            if (partieForm.getCapot()) {
                throw new TdaException("Pas de capot en cas de belge !!!");
            }
            if (partieForm.getChelem()) {
                throw new TdaException("Pas de chelem en cas de belge !!!");
            }
        }

        if (partieForm.getContratId() > 1) {
            if (partieForm.getPreneurId() == 0) {
                throw new TdaException("Il n'y a pas preneur indiqué !!!");
            }
            if (nombreJoueur > 4) {
                if (partieForm.getAppelId() == 0) {
                    throw new TdaException("Il n'y a pas appel indiqué !!!");
                }
            }

        }

        if (nombreJoueur == 4) {
            if(partieForm.getAppelId()!=0){
                throw new TdaException("Il n'y a pas appel à 4 joueurs !!!");
            }
            if(partieForm.getMortId()!=0){
                throw new TdaException("Il n'y a pas mort à 4 joueurs !!!");
            }
        }
        if (nombreJoueur == 5) {
            if(partieForm.getMortId()!=0){
                throw new TdaException("Il n'y a pas mort à 5 joueurs !!!");
            }
        }

        if (nombreJoueur == 6) {
            if (partieForm.getMortId() == 0) {
                throw new TdaException("Il n'y a pas mort indiqué !!!");
            }
            if (Objects.equals(partieForm.getPreneurId(), partieForm.getMortId())) {
                throw new TdaException("Le preneur et le mort sont identiques !!!");
            }
            if (Objects.equals(partieForm.getAppelId(), partieForm.getMortId())) {
                throw new TdaException("L'appel et le mort sont identiques !!!");
            }
            if (Objects.equals(partieForm.getPetitAuBoutId(), partieForm.getMortId())) {
                throw new TdaException("Le petit au bout et le mort sont identiques !!!");
            }
        }
    }

}
