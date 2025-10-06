package com.subsTracker.subs.controller;

import com.subsTracker.subs.model.User;
import com.subsTracker.subs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @PostMapping("/register")
    public User register(@Valid  @RequestBody User user) {
        return service.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody User loginData) {
        return service.loginUser(loginData.getEmail(), loginData.getPassword());
    }
}
