package com.mr486.tdawebui.controller;

import com.mr486.tdawebui.dto.AmiListe;
import com.mr486.tdawebui.socket.ServerStateWsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
@Slf4j
public class PartieController {

    private final ServerStateWsController serverStateWsController;

    private final RestTemplate restTemplate;
    @Value( "${app.tda-core.api-url}")
    private String coreApiUrl;

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
}
