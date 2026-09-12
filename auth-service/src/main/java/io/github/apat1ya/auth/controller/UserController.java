package io.github.apat1ya.auth.controller;

import io.github.apat1ya.auth.dto.ChangeEmailRequest;
import io.github.apat1ya.auth.dto.ConfirmEmailChangeRequest;
import io.github.apat1ya.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/me")
public class UserController {
    private final UserService userService;

    @PostMapping("/change-newEmail")
    public ResponseEntity<String> changeEmail(@Valid @RequestBody ChangeEmailRequest emailRequest) {
        userService.requestEmailChange(emailRequest);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Confirmation newEmail has been sent");
    }

    @PostMapping("/newEmail/change/confirm")
    public ResponseEntity<String> confirmEmailChange (@RequestBody ConfirmEmailChangeRequest changeRequest) {
        userService.confirmEmailChange(changeRequest.token());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Email successfully changed");
    }
}
