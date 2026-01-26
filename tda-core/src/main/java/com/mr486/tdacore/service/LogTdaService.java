package com.mr486.tdacore.service;

import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.persistance.LogTda;
import com.mr486.tdacore.repository.LogTdaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogTdaService {

    private final LogTdaRepository logTdaRepository;

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
        String hhmm = timeLog.toLocalDateTime()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        logTdaToString.append(hhmm).append(" ");

        if(actionCode==1){
            logTdaToString.append("✅");
        }
        if(actionCode==2){
            logTdaToString.append("✏");
        }
        logTdaToString.append(":").append(logTda.getNumeroPartie());
        return logTdaToString.toString();
    }

    public List<String> getLogs() {
        List<String> logs = new java.util.ArrayList<>();
        findAllLogsOrderByDateDesc().forEach(log -> logs.add(convertLogToString(log)));
        return logs;
    }
}
