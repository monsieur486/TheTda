package com.mr486.tdacore.controller;

import com.mr486.tdacore.dto.AmiListe;
import com.mr486.tdacore.service.AmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private")
public class AmiController {

    private final AmiService amiService;

    @GetMapping(value = "/amis", produces = "application/json")
    public ResponseEntity<List<AmiListe>> getListeAmis() {
        List<AmiListe> amis = amiService.getListeAmis();
        return ResponseEntity.ok(amis);
    }
}
