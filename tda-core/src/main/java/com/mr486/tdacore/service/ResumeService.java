package com.mr486.tdacore.service;

import com.mr486.tdacore.persistance.Partie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final PartieService partieService;
    private final ContratService contratService;

    public String createResume() {
        if(partieService.getNbPartie() == 0){
            return "En attente de la 1ère partie...";
        }
        return partieService.getNbPartie() + "\uD83C\uDFB2"
                + ": "
                + getDetail();
    }

    private String getDetail(){
        List<Partie> parties = partieService.getAllParties();
        int nbBelges = 0;
        int nbPetites = 0;
        int nbGardes = 0;
        int nbGardesSans = 0;
        int nbGardesContre = 0;
        int txParties = 0;
        int reussite = 0;
        int autogoal = 0;
        int pab = 0;
        int chelem = 0;
        int capot = 0;

        for(Partie partie : parties){
            if(partie.getContratId() == 1){
                nbBelges++;
            }else if(partie.getContratId() == 2){
                nbPetites++;
            }else if(partie.getContratId() == 3){
                nbGardes++;
            }else if(partie.getContratId() == 4){
                nbGardesSans++;
            }else if(partie.getContratId() == 5){
                nbGardesContre++;
            }

            if(partie.getContratId() != 1){
                txParties++;

                if(partie.getEstFait()){
                    reussite++;
                }

                if(Objects.equals(partie.getPreneurId(), partie.getAppelId())){
                    autogoal++;
                }
            }

            if(partie.getPetitAuBoutId() != 0){
                pab++;
            }

            if(partie.getChelem()){
                chelem++;
            }

            if(partie.getCapot()){
                capot++;
            }
        }
        StringBuilder resume = new StringBuilder();
        if(nbBelges > 0){
            resume.append(nbBelges).append(contratService.getContratById(1).getInitiale()).append(" ");
        }
        if(nbPetites > 0){
            resume.append(nbPetites).append(contratService.getContratById(2).getInitiale()).append(" ");
        }
        if(nbGardes > 0){
            resume.append(nbGardes).append(contratService.getContratById(3).getInitiale()).append(" ");
        }
        if(nbGardesSans > 0){
            resume.append(nbGardesSans).append(contratService.getContratById(4).getInitiale()).append(" ");
        }
        if(nbGardesContre > 0){
            resume.append(nbGardesContre).append(contratService.getContratById(5).getInitiale()).append(" ");
        }
        resume.append("\n");
        if(autogoal > 0){
            resume.append(autogoal).append("⚽ ");
        }
        if(pab > 0){
            resume.append(pab).append("1️⃣ ");
        }
        if(chelem > 0){
            resume.append(chelem).append("\uD83D\uDC51 ");
        }
        if(capot > 0){
            resume.append(capot).append("\uD83D\uDE2D ");
        }
        resume.append(getTxReussite(txParties, reussite));

        return resume.toString();
    }


    private String getTxReussite(int parties, int reussite){
        int tx = (int)  ((reussite / (double) parties) * 100);
        if(tx < 25){
            return "\uD83D\uDD34" + tx + "%";
        }else if(tx < 50){
            return "\uD83D\uDFE0" + tx + "%";
        }else if(tx < 75){
            return "\uD83D\uDFE1" + tx + "%";
        }else{
            return "\uD83D\uDFE2" + tx + "%";
        }
    }
}
