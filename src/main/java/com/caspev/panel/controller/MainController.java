package com.caspev.panel.controller;

import com.caspev.panel.security.RolesConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured({RolesConstants.ADMIN})
public class MainController {

    @GetMapping("/")
    public String redirectToDefaultController() {
        return "redirect:/event-logs";
    }
}
