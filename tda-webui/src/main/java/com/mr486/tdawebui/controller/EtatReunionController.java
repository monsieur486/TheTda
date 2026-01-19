package com.mr486.tdawebui.controller;

import com.mr486.tdawebui.dto.EtatReunion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/public")
@Slf4j
@RequiredArgsConstructor
public class EtatReunionController {

    private final RestTemplate restTemplate;
    @Value( "${app.tda-core.api-url}")
    private String coreApiUrl;

    @RequestMapping("/etatReunion")
    public ResponseEntity<EtatReunion> getEtatReunion() {
        try {
            return restTemplate.exchange(coreApiUrl + "/api/public/etatReunion", HttpMethod.GET, null, EtatReunion.class);
        } catch (Exception e) {
            log.error("Error fetching etat reunion from core API", e);
            return ResponseEntity.status(500).build();
        }
    }
}
