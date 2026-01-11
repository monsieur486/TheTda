package com.mr486.tdacore.controller;

import com.mr486.tdacore.dto.ContratListe;
import com.mr486.tdacore.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private")
public class ContratController {

    private final ContratService contratService;

    @GetMapping(value = "/contrats", produces = "application/json")
    public ResponseEntity<List<ContratListe>> getContrats() {
        return ResponseEntity.ok(contratService.getContrats());
    }
}
