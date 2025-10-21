package com.subsTracker.subs.controller;

import com.subsTracker.subs.service.UsageReportService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usage")
@RequiredArgsConstructor
public class UsageReportController {
    private final UsageReportService usageService;

    @PostMapping
    public ResponseEntity<String> addUsage(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody UsageRequest req
    ) {
        usageService.saveUsage(user.getUsername(), req.getServiceName(), req.getTotalMinutes(), req.getSource());
        return ResponseEntity.ok("Usage saved");
    }

    @GetMapping("/{serviceName}")
    public ResponseEntity<Integer> getUsageForService(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String serviceName
    ) {
        int total = usageService.getMonthlyUsage(user.getUsername(), serviceName);
        return ResponseEntity.ok(total);
    }

    @Data
    public static class UsageRequest {
        private String serviceName;
        private int totalMinutes;
        private String source;
    }
}
