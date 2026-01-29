package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.persistance.LogTda;
import com.mr486.tdacore.repository.LogTdaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogTdaService {

    private final LogTdaRepository logTdaRepository;
    private final ContratService contratService;
    private final AmiService amiService;
    private final JoueurService joueurService;

    private List<LogTda> findAllLogsOrderByDateDesc() {
        return logTdaRepository.findAllByOrderByDateCreationDesc();
    }

    private void saveLog(LogTda logTda) {
        if (logTda.getDateCreation() == null) {
            logTda.setDateCreation(java.sql.Timestamp.from(java.time.Instant.now()));
        }
        logTdaRepository.save(logTda);
    }

    public void deleteAllLogs() {
        logTdaRepository.deleteAll();
    }

    private LogTda getLogFromPartie(Integer actionCode, PartieForm partieForm, int numPartie) {
        LogTda logTda = new LogTda();
        logTda.setActionCode(actionCode);
        logTda.setNumeroPartie(numPartie);
        logTda.setContratId(partieForm.getContratId());
        logTda.setPreneurId(partieForm.getPreneurId());
        logTda.setAppelId(partieForm.getAppelId());
        logTda.setMortId(partieForm.getMortId());
        logTda.setEstFait(partieForm.getEstFait());
        logTda.setScore(partieForm.getScore());
        logTda.setPetitAuBoutId(partieForm.getPetitAuBoutId());
        logTda.setChelem(partieForm.getChelem());
        logTda.setCapot(partieForm.getCapot());
        return logTda;
    }

    public void addLog(Integer actionCode, PartieForm partieForm, int numPartie) {
        saveLog(getLogFromPartie(actionCode, partieForm, numPartie));
    }

    private String convertLogToString(LogTda logTda) {
        StringBuilder logTdaToString = new StringBuilder();
        int actionCode = logTda.getActionCode();
        Timestamp timeLog = logTda.getDateCreation();
        String hhmm = timeLog.toInstant()
                .atZone(java.time.ZoneId.of("Europe/Paris"))
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        logTdaToString.append(hhmm).append(" ");

        if (actionCode == 1) {
            logTdaToString.append("\uD83C\uDD95");
        }
        if (actionCode == 2) {
            logTdaToString.append("✏️");
        }
        logTdaToString.append(logTda.getNumeroPartie());
        logTdaToString.append(" ").append(getDetailByLog(logTda));
        return logTdaToString.toString();
    }

    public List<String> getLogs() {
        List<String> logs = new java.util.ArrayList<>();
        findAllLogsOrderByDateDesc().forEach(log -> logs.add(convertLogToString(log)));
        return logs;
    }

    private String getDetailByLog(LogTda partie) {
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
