package io.github.apat1ya.auth.controller;

import io.github.apat1ya.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/internal")
@RequiredArgsConstructor
public class InternalUserController {
    private final UserService userService;

    @GetMapping("/internal/users/by-email")
    public Long findUserIdByEmail(@RequestParam String email) {
        return userService.findUserIdByEmail(email);
    }
}
