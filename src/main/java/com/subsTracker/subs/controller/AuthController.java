package com.subsTracker.subs.controller;

import com.subsTracker.subs.model.User;
import com.subsTracker.subs.service.AuthService;
import com.subsTracker.subs.dto.AuthRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest request) {
        String message = authService.register(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

}
