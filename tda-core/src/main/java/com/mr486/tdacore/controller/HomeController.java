package com.mr486.tdacore.controller;

import com.mr486.tdacore.service.EtatReunionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final EtatReunionService etatReunionService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("etatReunion", etatReunionService.getEtatServeur());
        return "home";
    }
}
