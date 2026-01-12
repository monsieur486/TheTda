package com.mr486.tdacore.controller;

import com.mr486.tdacore.service.GameManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/game")
public class GameController {

    private final GameManagement gameManagement;

    @PostMapping("/create")
    public ResponseEntity<Void> creerReunion(@RequestBody List<Integer> amiIds) {
        gameManagement.createReunion(amiIds);
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

}
