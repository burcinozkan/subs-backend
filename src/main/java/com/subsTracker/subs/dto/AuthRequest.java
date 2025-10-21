package com.subsTracker.subs.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    @JsonProperty("fullName")
    private String fullName;
}
