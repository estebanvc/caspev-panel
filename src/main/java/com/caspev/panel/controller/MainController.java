package com.caspev.panel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToDefaultController() {
        return "redirect:/nfc-cards";
    }
}
