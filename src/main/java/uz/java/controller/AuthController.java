package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.java.config.CustomUserDetails;
import uz.java.dto.auth.RegisterRequest;
import uz.java.dto.auth.TokenResponse;
import uz.java.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(service.login(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
}
