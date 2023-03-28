package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String getLogin(final ModelMap model, @RequestParam("error") final Optional<String> error) {
        error.ifPresent(e -> model.addAttribute("error", e));
        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String logOutSuccess() {
        return "logout";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
