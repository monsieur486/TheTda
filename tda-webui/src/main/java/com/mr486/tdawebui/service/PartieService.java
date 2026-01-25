package com.mr486.tdawebui.service;

import com.mr486.tdawebui.dto.AmiListe;
import com.mr486.tdawebui.dto.ContratListe;
import com.mr486.tdawebui.dto.JoueurListe;
import com.mr486.tdawebui.dto.PartieForm;
import com.mr486.tdawebui.socket.ServerStateWsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartieService {

    private final ServerStateWsController serverStateWsController;

    private final RestTemplate restTemplate;
    @Value( "${app.tda-core.api-url}")
    private String coreApiUrl;

    private ResponseEntity<ContratListe[]> getContrats() {
        try {
            return restTemplate.exchange(coreApiUrl + "/api/private/contrats", HttpMethod.GET, null, ContratListe[].class);
        } catch (Exception e) {
            log.error("Error fetching contrats from core API", e);
            return ResponseEntity.status(500).build();
        }
    }

    public List<ContratListe> getContratsListe() {
        return List.of(Objects.requireNonNull(getContrats().getBody()));
    }

    private ResponseEntity<JoueurListe[]> getJoueurs() {
        try {
            return restTemplate.exchange(coreApiUrl + "/api/private/joueurs", HttpMethod.GET, null, JoueurListe[].class);
        } catch (Exception e) {
            log.error("Error fetching joueurs from core API", e);
            return ResponseEntity.status(500).build();
        }
    }

    public List<JoueurListe> getJoueursListe() {
        return List.of(Objects.requireNonNull(getJoueurs().getBody()));
    }

    public ResponseEntity<Void> ajoutPartie(PartieForm partieForm) {
        HttpEntity<PartieForm> request = new HttpEntity<>(partieForm);
        ResponseEntity<Void> response = restTemplate.exchange(
                coreApiUrl + "/api/private/game/partie",
                HttpMethod.POST,
                request,
                Void.class
        );
        serverStateWsController.broadcastState();
        return response;
    }

    public ResponseEntity<Void> updatePartie(int numPartie, PartieForm partieForm) {
        HttpEntity<PartieForm> request = new HttpEntity<>(partieForm);
        ResponseEntity<Void> response = restTemplate.exchange(
                coreApiUrl + "/api/private/game/partie/" + numPartie,
                HttpMethod.POST,
                request,
                Void.class
        );
        serverStateWsController.broadcastState();
        return response;
    }

    public ResponseEntity<PartieForm> getPartie(int numPartie) {
        try {
            return restTemplate.exchange(
                    coreApiUrl + "/api/private/game/partie/" + numPartie,
                    HttpMethod.GET,
                    null,
                    PartieForm.class
            );
        } catch (Exception e) {
            log.error("Error fetching partie from core API", e);
            return ResponseEntity.status(500).build();
        }
    }

    public int joueursInscrits() {
        return getJoueursListe().size();
    }

    public void deleteParties() {
        restTemplate.delete(coreApiUrl + "/api/private/game/partie");
    }
}
