package fr.parisnanterre.greentrip.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    @PreAuthorize("authentication.principal.username == 'admin@example.com'")
    public String swaggerRedirect() {
        return "redirect:/swagger-ui.html";
    }
}
