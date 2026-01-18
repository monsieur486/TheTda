package com.mr486.tdacore.controller;

import com.mr486.tdacore.dto.ContratListe;
import com.mr486.tdacore.dto.JoueurListe;
import com.mr486.tdacore.dto.PartieForm;
import com.mr486.tdacore.service.ContratService;
import com.mr486.tdacore.service.JoueurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartieController {

    private final ContratService contratService;
    private final JoueurService joueurService;


    @GetMapping("/admin/partie")
    public String publicView(Model model) {
        int nbreJoueurs = joueurService.getNbJoueur();
        List<ContratListe> contrats = contratService.getContrats();
        List<JoueurListe> joueurs = joueurService.getJoueursInscrits();
        model.addAttribute("nbreJoueurs", nbreJoueurs);
        model.addAttribute("contrats", contrats);
        model.addAttribute("partieForm", new PartieForm());
        model.addAttribute("joueurs", joueurs);

        return "partie";
    }
}
