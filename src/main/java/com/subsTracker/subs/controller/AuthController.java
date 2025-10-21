package com.subsTracker.subs.controller;

import com.subsTracker.subs.model.User;
import com.subsTracker.subs.service.AuthService;
import com.subsTracker.subs.dto.AuthRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody AuthRequest request) {
        String message = authService.register(request.getEmail(), request.getPassword(), request.getFullName());
        System.out.println("📩 FullName from request: " + request.getFullName());

        return ResponseEntity.ok(Map.of("message", message));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = authService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/settings")
    public ResponseEntity<User> updateSettings(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map <String, Object> updates
    ){
        User user = authService.getUserByEmail(userDetails.getUsername());


        if (updates.containsKey("language")) {
            user.setLanguage((String) updates.get("language"));
        }
        if (updates.containsKey("currency")) {
            user.setCurrency((String) updates.get("currency"));
        }
        if (updates.containsKey("pushNotifications")) {
            user.setPushNotifications((Boolean) updates.get("pushNotifications"));
        }

        authService.saveUser(user);
        return ResponseEntity.ok(user);
    }
}
