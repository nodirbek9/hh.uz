package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.java.config.CustomUserDetails;
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

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(service.refresh(refreshToken));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> logout(@AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.ok(service.logout(principal.getUsername()));
    }
}
