package com.subsTracker.subs.dto;



import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
