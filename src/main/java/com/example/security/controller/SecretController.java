package com.example.security.controller;

import com.example.security.domain.Secret;
import com.example.security.repository.SecretRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SecretController {

    private final SecretRepository secretRepository;
    private final HttpServletRequest request;

    @GetMapping("/secret")
    public String secretForm() {
        return "secretForm";
    }

    @PostMapping("/secret")
    public String submitSecret(@RequestBody final String secretText, final Model model) {
        final Secret secret = Secret.builder()
                                    .link(UUID.randomUUID().toString())
                                    .secretText(secretText)
                                    .createdDate(new Date(System.currentTimeMillis()))
                                    .build();

        secretRepository.save(secret);
        final String baseUrl = request.getRequestURL().toString();
        final String link = baseUrl + "/" + secret.getLink();
        model.addAttribute("link", link);

        return "/showLink";
    }

    @GetMapping("/secret/{link}")
    public String showSecret(@PathVariable final String link, final Model model) {
        Optional<Secret> optionalSecret = secretRepository.findByLink(link);

        if (optionalSecret.isEmpty()) {
            return "error";
        }

        final Secret secret = optionalSecret.get();
        model.addAttribute("secretMessage", secret.getSecretText());
        secretRepository.delete(secret);

        return "showSecret";
    }

}
