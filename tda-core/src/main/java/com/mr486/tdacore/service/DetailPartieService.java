package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.persistance.LogTda;
import com.mr486.tdacore.persistance.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DetailPartieService {

    private final PartieService partieService;
    private final JoueurService joueurService;
    private final ContratService contratService;
    private final AmiService amiService;

    private String getDetailPartie(Partie partie) {
        int nbJoueurs = joueurService.getNbJoueur();

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(partie.getNumeroPartie()).append(") ");
        sb.append(contratService.getContratById(partie.getContratId()).getInitiale());
        if (partie.getContratId() > 1) {
            sb.append(" ");
            if (nbJoueurs == 4) {
                sb.append(amiService.getAmiById(partie.getPreneurId()).getNom());
            } else {
                if (Objects.equals(partie.getPreneurId(), partie.getAppelId())) {
                    sb.append(amiService.getAmiById(partie.getPreneurId()).getNom()).append("⚽");
                } else {
                    sb.append(amiService.getAmiById(partie.getPreneurId()).getNom()).append("\uD83E\uDD1D");
                    sb.append(amiService.getAmiById(partie.getAppelId()).getNom());
                }
            }
            if (partie.getEstFait()) {
                sb.append(" \uD83D\uDFE2");
            } else {
                sb.append(" \uD83D\uDD34");
            }
            sb.append(partie.getScore());
            if (partie.getPetitAuBoutId() > 0) {
                sb.append(" 1️⃣ ");
                sb.append(amiService.getAmiById(partie.getPetitAuBoutId()).getNom());
            }
            if (partie.getChelem()) {
                sb.append(" \uD83D\uDC51Chelem");
            }
            if (partie.getCapot()) {
                sb.append(" \uD83D\uDE2DCapot");
            }

        }
        if (nbJoueurs == 6) {
            sb.append(" \uD83E\uDEA6").append(amiService.getAmiById(partie.getMortId()).getNom());
        }

        return sb.toString();
    }

    public List<String> getDetails() {
        List<Partie> parties = partieService.getAllParties();
        List<String> details = new java.util.ArrayList<>();
        for (Partie partie : parties) {
            details.add(getDetailPartie(partie));
        }
        return details;
    }

    public String getDetailPartieLog(LogTda partie) {
        int nbJoueurs = joueurService.getNbJoueur();

        StringBuilder sb = new StringBuilder();
        sb.append(contratService.getContratById(partie.getContratId()).getInitiale());
        if (partie.getContratId() > 1) {
            sb.append(" ");
            if (nbJoueurs == 4) {
                sb.append(amiService.getAmiById(partie.getPreneurId()).getNom());
            } else {
                if (Objects.equals(partie.getPreneurId(), partie.getAppelId())) {
                    sb.append(amiService.getAmiById(partie.getPreneurId()).getNom()).append("⚽");
                } else {
                    sb.append(amiService.getAmiById(partie.getPreneurId()).getNom()).append("\uD83E\uDD1D");
                    sb.append(amiService.getAmiById(partie.getAppelId()).getNom());
                }
            }
            if (partie.getEstFait()) {
                sb.append(" \uD83D\uDFE2");
            } else {
                sb.append(" \uD83D\uDD34");
            }
            sb.append(partie.getScore());
            if (partie.getPetitAuBoutId() > 0) {
                sb.append(" 1️⃣ ");
                sb.append(amiService.getAmiById(partie.getPetitAuBoutId()).getNom());
            }
            if (partie.getChelem()) {
                sb.append(" \uD83D\uDC51Chelem");
            }
            if (partie.getCapot()) {
                sb.append(" \uD83D\uDE2DCapot");
            }

        }
        if (nbJoueurs == 6) {
            sb.append(" \uD83E\uDEA6").append(amiService.getAmiById(partie.getMortId()).getNom());
        }

        return sb.toString();
    }
}
