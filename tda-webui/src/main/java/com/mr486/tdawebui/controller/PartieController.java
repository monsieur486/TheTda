package com.mr486.tdawebui.controller;

import com.mr486.tdawebui.dto.ErrorMessage;
import com.mr486.tdawebui.dto.PartieForm;
import com.mr486.tdawebui.service.PartieService;
import com.mr486.tdawebui.tools.ErrorResponseTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
@Slf4j
public class PartieController {

    private final PartieService partieService;
    private final ErrorResponseTools errorResponseTools;

    @GetMapping("/partie")
    public String getReunion(Model model) {
        model.addAttribute("joueurs", partieService.getJoueursListe());
        model.addAttribute("nbJoueurs", partieService.joueursInscrits());
        model.addAttribute("contrats", partieService.getContratsListe());
        model.addAttribute("partieForm", new PartieForm());
        return "addPartie";
    }

    @GetMapping("/partie/{numPartie}")
    public String getPartie(@PathVariable int numPartie, Model model, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<PartieForm> response = partieService.getPartie(numPartie);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                model.addAttribute("numPartie", numPartie);
                model.addAttribute("joueurs", partieService.getJoueursListe());
                model.addAttribute("nbJoueurs", partieService.joueursInscrits());
                model.addAttribute("contrats", partieService.getContratsListe());
                model.addAttribute("partieForm", response.getBody());
                return "updatePartie";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Chargement impossible (statut: " + response.getStatusCode() + ").");
            return "redirect:/admin/reunion";

        } catch (HttpStatusCodeException e) {
            String json = e.getResponseBodyAsString();
            ErrorMessage err = errorResponseTools.getErrorMessageFromJson(json, "tda-core");

            redirectAttributes.addFlashAttribute("errorMessage", err.getMessage());
            return "redirect:/admin/reunion";
        }
    }

    @PostMapping("/partie/")
    public String ajoutPartie(@ModelAttribute PartieForm partieForm,
                              RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<Void> response = partieService.ajoutPartie(partieForm);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Création impossible (statut: " + response.getStatusCode() + ").");
            return "redirect:/admin/partie";

        } catch (HttpStatusCodeException e) {
            String json = e.getResponseBodyAsString(); // <-- contient: {"status":400,"message":"..."}
            ErrorMessage err = errorResponseTools.getErrorMessageFromJson(json, "tda-core");

            redirectAttributes.addFlashAttribute("errorMessage", err.getMessage());
            return "redirect:/admin/partie";
        }
    }

    @PostMapping("/partie/{numPartie}")
    public String updatePartie(@PathVariable int numPartie,
                               @ModelAttribute PartieForm partieForm,
                               RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<Void> response = partieService.updatePartie(numPartie, partieForm);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/admin/reunion";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Création impossible (statut: " + response.getStatusCode() + ").");

            return "redirect:/admin/partie/" + numPartie;
        } catch (HttpStatusCodeException e) {
            String json = e.getResponseBodyAsString();
            ErrorMessage err = errorResponseTools.getErrorMessageFromJson(json, "tda-core");
            redirectAttributes.addFlashAttribute("errorMessage", err.getMessage());
            return "redirect:/admin/partie/" + numPartie;
        }
    }
}
