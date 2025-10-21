package com.subsTracker.subs.service;

import com.subsTracker.subs.model.User;
import com.subsTracker.subs.repository.UserRepository;
import com.subsTracker.subs.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository repo, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = new BCryptPasswordEncoder();
        this.jwtTokenProvider=jwtTokenProvider;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User registerUser(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String loginUser(String email, String password) {
        var found = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(password, found.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtTokenProvider.generateToken(email);
    }
    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }




}
