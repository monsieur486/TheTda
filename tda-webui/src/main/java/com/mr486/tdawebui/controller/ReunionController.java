package com.mr486.tdawebui.controller;

import com.mr486.tdawebui.dto.AmiListe;
import com.mr486.tdawebui.dto.ErrorMessage;
import com.mr486.tdawebui.service.ReunionService;
import com.mr486.tdawebui.tools.ErrorResponseTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
@Slf4j
public class ReunionController {

    private final ReunionService reunionService;
    private final ErrorResponseTools errorResponseTools;

    @GetMapping("/reunion")
    public String getReunion(Model model) {
        int status = reunionService.reunionActiveStatus();
        model.addAttribute("status", status);
        List<AmiListe> amis = reunionService.getAmisListe();
        model.addAttribute("amis", amis);
        return "reunion";
    }

    @PostMapping("/reunion")
    public String createReunion(@RequestParam(name = "amiIds", required = false) List<Integer> amiIds,
                                RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<Void> response = reunionService.createReunion(amiIds);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Création impossible (statut: " + response.getStatusCode() + ").");
            return "redirect:/admin/reunion";

        } catch (HttpClientErrorException e) {
            String json = e.getResponseBodyAsString(); // <-- contient: {"status":400,"message":"..."}
            ErrorMessage err = errorResponseTools.getErrorMessageFromJson(json, "tda-core");

            redirectAttributes.addFlashAttribute("errorMessage", err.getMessage());
            return "redirect:/admin/reunion";
        }
    }

    @PostMapping("/reunion/cagnotte")
    public String cagnotte(RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<Void> response = reunionService.cagnotte();

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/admin/reunion";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cagnotte impossible (statut: " + response.getStatusCode() + ").");
            return "redirect:/admin/reunion";

        } catch (HttpClientErrorException e) {
            String json = e.getResponseBodyAsString();
            ErrorMessage err = errorResponseTools.getErrorMessageFromJson(json, "tda-core");

            redirectAttributes.addFlashAttribute("errorMessage", err.getMessage());
            return "redirect:/admin/reunion";
        }
    }

    @PostMapping("/reunion/raz")
    public String raz(RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<Void> response = reunionService.raz();

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/admin/reunion";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "RAZ impossible (statut: " + response.getStatusCode() + ").");
            return "redirect:/admin/reunion";

        } catch (HttpClientErrorException e) {
            String json = e.getResponseBodyAsString();
            ErrorMessage err = errorResponseTools.getErrorMessageFromJson(json, "tda-core");

            redirectAttributes.addFlashAttribute("errorMessage", err.getMessage());
            return "redirect:/admin/reunion";
        }
    }
}
