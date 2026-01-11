package com.mr486.tdacore.controller;

import com.mr486.tdacore.dto.EtatReunion;
import com.mr486.tdacore.service.EtatReunionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicController {

    private final EtatReunionService etatReunionService;

    @GetMapping(value = "/etatReunion", produces = "application/json")
    public ResponseEntity<EtatReunion> getEtatReunion() {
        return ResponseEntity.ok(etatReunionService.getEtatServeur());
    }
}
