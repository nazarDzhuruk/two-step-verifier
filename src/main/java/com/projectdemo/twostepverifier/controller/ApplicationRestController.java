package com.projectdemo.twostepverifier.controller;

import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.verification.AuthenticatorService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class ApplicationRestController {
    private final AuthenticatorService authenticatorService;

    public ApplicationRestController(AuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        authenticatorService.createUser(user);
    }

    @PutMapping("/verify/{userId}/{verifier}")
    public boolean verify(@PathVariable("userId") Integer userId, @PathVariable("verifier") Integer verifier) {
        return authenticatorService.verifyUser(userId, verifier);
    }
}
