package com.mr486.tdawebui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PublicController {

    @Value("${app.tda-core.api-url}")
    private String tdaCoreApiUrl;

    @GetMapping("/")
    public String publicView(Model model) {
        model.addAttribute("etape", 1);
        model.addAttribute("tdaCoreApiUrl", tdaCoreApiUrl + "/api/public/etatReunion");
        return "home";
    }
}
