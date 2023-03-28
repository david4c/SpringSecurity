package com.example.security.controller;

import com.example.security.config.dto.UserDto;
import com.example.security.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok(mainService.getInfo());
    }

    @GetMapping("/about")
    public ResponseEntity<String> getAbout() {
        return ResponseEntity.ok(mainService.getAbout());
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok(mainService.getAdmin());
    }

    @GetMapping("/blocked")
    public ResponseEntity<List<UserDto>> getBlockedUsers() {
        return ResponseEntity.ok(mainService.getBLockedUsers());
    }
}
