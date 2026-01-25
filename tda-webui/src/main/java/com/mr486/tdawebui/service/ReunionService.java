package com.mr486.tdawebui.service;

import com.mr486.tdawebui.dto.AmiListe;
import com.mr486.tdawebui.dto.EtatReunion;
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
public class ReunionService {

    private final ServerStateWsController serverStateWsController;

    private final RestTemplate restTemplate;
    @Value( "${app.tda-core.api-url}")
    private String coreApiUrl;

    private ResponseEntity<EtatReunion> getEtatReunion() {
        try {
            return restTemplate.exchange(coreApiUrl + "/api/public/etatReunion", HttpMethod.GET, null, EtatReunion.class);
        } catch (Exception e) {
            log.error("Error fetching etat reunion from core API", e);
            return ResponseEntity.status(500).build();
        }
    }

    public int reunionActiveStatus() {
        return Objects.requireNonNull(getEtatReunion().getBody()).getStatus();
    }

    private ResponseEntity<AmiListe[]> getAmis() {
        try {
            return restTemplate.exchange(coreApiUrl + "/api/private/amis", HttpMethod.GET, null, AmiListe[].class);
        } catch (Exception e) {
            log.error("Error fetching amis from core API", e);
            return ResponseEntity.status(500).build();
        }
    }

    public List<AmiListe> getAmisListe() {
        return List.of(Objects.requireNonNull(getAmis().getBody()));
    }

    public ResponseEntity<Void> createReunion(List<Integer> amiIds) {
        HttpEntity<List<Integer>> request = new HttpEntity<>(amiIds);
        ResponseEntity<Void> response = restTemplate.exchange(
                coreApiUrl + "/api/private/game/create",
                HttpMethod.POST,
                request,
                Void.class
        );
        serverStateWsController.broadcastState();
        return response;
    }

    public ResponseEntity<Void> cagnotte() {
        ResponseEntity<Void> response = restTemplate.exchange(
                coreApiUrl + "/api/private/game/cagnotte",
                HttpMethod.POST,
                null,
                Void.class
        );
        serverStateWsController.broadcastState();
        return response;
    }

    public ResponseEntity<Void> raz() {
        ResponseEntity<Void> response = restTemplate.exchange(
                coreApiUrl + "/api/private/game/raz",
                HttpMethod.POST,
                null,
                Void.class
        );
        serverStateWsController.broadcastState();
        return response;
    }

}
