package com.mr486.tdacore.controller;

import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.service.GameManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/game")
public class GameController {

    private final GameManagement gameManagement;

    @PostMapping("/create")
    public ResponseEntity<Void> creerReunion(@RequestBody List<Integer> amiIds) {
        gameManagement.creation(amiIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/raz")
    public ResponseEntity<Void> raz() {
        gameManagement.raz();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cagnotte")
    public ResponseEntity<Void> cagnotte() {
        gameManagement.cagnotte();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/partie")
    public ResponseEntity<Void> partie(@RequestBody PartieForm partieForm) {
        gameManagement.addPartie(partieForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/partie/{numPartie}")
    public ResponseEntity<Void> partie(@PathVariable int numPartie, @RequestBody PartieForm partieForm) {
        gameManagement.updatePartie(numPartie, partieForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/partie/{numPartie}", produces = "application/json")
    public ResponseEntity<PartieForm> getPartieFormByNumPartie(@PathVariable int numPartie) {
        PartieForm partieForm = gameManagement.getPartieFormByNumPartie(numPartie);
        return ResponseEntity.ok(partieForm);
    }

}
