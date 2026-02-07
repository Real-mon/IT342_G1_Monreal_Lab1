package backendApplication.example.backend.controller;

import backendApplication.example.backend.dto.AuthResponse;
import backendApplication.example.backend.dto.LoginRequest;
import backendApplication.example.backend.dto.RegisterRequest;
import backendApplication.example.backend.model.User;
import backendApplication.example.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            User u = authService.registerUser(req);
            return ResponseEntity.ok(u.getUsername());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            String token = authService.authenticate(req);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return ResponseEntity.badRequest().body("No token provided");
        String token = authHeader.substring(7);
        authService.invalidateToken(token);
        return ResponseEntity.ok("Logged out");
    }
}