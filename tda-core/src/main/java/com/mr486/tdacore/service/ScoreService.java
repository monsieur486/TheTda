package com.mr486.tdacore.service;

import com.mr486.tdacore.configuration.ApplicationConfiguration;
import com.mr486.tdacore.dto.JoueurListe;
import com.mr486.tdacore.dto.PointJoueur;
import com.mr486.tdacore.persistance.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final PartieService partieService;
    private final JoueurService joueurService;
    private final ToolScoreService toolScoreService;
    private final ReunionService reunionService;
    private final AmiService amiService;

    public List<PointJoueur> getScores() {
        int nbJoueurs = joueurService.getNbJoueur();
        List<JoueurListe> joueurs = joueurService.getJoueursInscrits();
        List<Partie> parties = partieService.getAllParties();
        return calculScore(nbJoueurs, parties, joueurs);
    }

    private List<PointJoueur> calculScore(int nbJoueurs, List<Partie> parties, List<JoueurListe> joueurs) {
        List<PointJoueur> scores = new ArrayList<>();
        for (JoueurListe joueur : joueurs) {
            int points = 0;
            for (Partie partie : parties) {
                points += toolScoreService.calculScoreJoueur(joueur.getId(), partie, nbJoueurs);
            }
            scores.add(colorScore(joueur, points));
        }
        return scores.stream()
                .sorted(Comparator
                        .comparing(PointJoueur::getScore)
                        .thenComparing(PointJoueur::getNom))
                .collect(Collectors.toList());
    }

    private PointJoueur colorScore(JoueurListe joueur, int score) {
        PointJoueur pointColor = new PointJoueur();
        String nom = joueur.getNom();
        if (reunionService.reunionActiveStatus() == 3) {
            if (!estGuest(joueur.getId())) {
                nom += cagnotte(score);
            }
        }
        pointColor.setNom(nom);
        pointColor.setAvatar(avatarUrl(joueur.getId()));
        pointColor.setScore(score);
        if (score > 0) {
            pointColor.setColor(ApplicationConfiguration.COLOR_POSITIVE);
        } else if (score < 0) {
            pointColor.setColor(ApplicationConfiguration.COLOR_NEGATIVE);
        } else {
            pointColor.setColor(ApplicationConfiguration.COLOR_NEUTRAL);
        }
        return pointColor;
    }

    private Boolean estGuest(Integer id) {
        return amiService.getAmiById(id).getIsGuest();
    }

    private String avatarUrl(Integer id) {
        return "/img/" + amiService.getAvatarUrl(id);
    }

    private String cagnotte(int score) {
        if (score > -10) {
            return " 2.00 €";
        } else {
            score = score * -1;
            double cagnotte = Math.abs(2000 + score * 3) / 1000.0;
            cagnotte = Math.round(cagnotte * 10.0) / 10.0;
            String resultat = String.format("%.2f", cagnotte);
            return " " + resultat + " €";
        }
    }
}
