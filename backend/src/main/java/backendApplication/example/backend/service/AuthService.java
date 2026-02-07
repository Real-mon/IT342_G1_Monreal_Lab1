package backendApplication.example.backend.service;

import backendApplication.example.backend.dto.LoginRequest;
import backendApplication.example.backend.dto.RegisterRequest;
import backendApplication.example.backend.model.User;
import backendApplication.example.backend.repository.UserRepository;
import backendApplication.example.backend.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    // simple token blacklist for logout (in-memory)
    private final Set<String> invalidatedTokens = new HashSet<>();

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public User registerUser(RegisterRequest req) {
        Optional<User> byUsername = userRepository.findByUsername(req.getUsername());
        if (byUsername.isPresent()) throw new IllegalArgumentException("Username already taken");
        Optional<User> byEmail = userRepository.findByEmail(req.getEmail());
        if (byEmail.isPresent()) throw new IllegalArgumentException("Email already in use");
        String hashed = passwordEncoder.encode(req.getPassword());
        User u = new User(req.getUsername(), req.getEmail(), req.getFullName(), hashed);
        return userRepository.save(u);
    }

    public String authenticate(LoginRequest req) {
        User user = userRepository.findByUsername(req.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
        return tokenProvider.createToken(user.getUsername());
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}