package com.mr486.tdacore.service;

import com.mr486.tdacore.persistance.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolScoreService {

    private final ContratService contratService;

    public int calculScoreJoueur(int joueurId, Partie partie, int nbJoueurs) {
        int delta = 0;
        if (partie.getContratId() == 1) {
            return 0;
        }
        if (nbJoueurs == 6) {
            if (joueurId == partie.getMortId()) {
                return 0;
            }
        }

        int pointsPartie = calculPointsPartie(partie);

        if (nbJoueurs == 4) {
            if (joueurId == partie.getPreneurId()) {
                delta += pointsPartie * 3;
            } else {
                delta -= pointsPartie;
            }
        } else {
            if (joueurId == partie.getPreneurId()) {
                if (joueurId == partie.getAppelId()) {
                    delta += pointsPartie * 4;
                } else {
                    delta += pointsPartie * 2;
                }
            } else if (joueurId == partie.getAppelId()) {
                delta += pointsPartie;
            } else {
                delta -= pointsPartie;
            }
        }

        if (partie.getPetitAuBoutId() != 0) {
            if (joueurId == partie.getPetitAuBoutId()) {
                if (nbJoueurs == 4) {
                    delta += 30;
                } else {
                    delta += 40;
                }
            } else {
                delta -= 10;
            }
        }

        return delta;
    }

    public int calculPointsPartie(Partie partie) {
        int points = contratService.getContratById(partie.getContratId()).getPoints();
        points += partie.getScore();
        if (!partie.getEstFait()) {
            points = -1 * points;
        }
        if (partie.getChelem() || partie.getCapot()) {
            points = points * 2;
        }
        return points;
    }

    public List<Integer> listeDelta(int nbJoueurs, List<Partie> parties, int joueurId) {
        List<Integer> scores = new ArrayList<>();
        scores.add(0);
        for (Partie partie : parties) {
            scores.add(calculScoreJoueur(joueurId, partie, nbJoueurs));
        }
        return scores;
    }
}
