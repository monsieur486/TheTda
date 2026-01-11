package com.mr486.tdacore.controller;

import com.mr486.tdacore.dto.EtatReunion;
import com.mr486.tdacore.dto.JoueurListe;
import com.mr486.tdacore.service.JoueurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private")
public class JoueurController {

    private final JoueurService joueurService;

    @GetMapping(value = "/joueurs", produces = "application/json")
    public ResponseEntity<List<JoueurListe>> getJoueurs () {
        return ResponseEntity.ok(joueurService.getJoueursInscrits());
    }

    @PostMapping("/joueurs")
    public ResponseEntity<Void> creerJoueurs(@RequestBody List<Integer> amiIds) {
        joueurService.createPartie(amiIds);
        return ResponseEntity.ok().build();
    }
}
