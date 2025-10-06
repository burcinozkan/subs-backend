package com.subsTracker.subs.controller;

import com.subsTracker.subs.model.Subscription;
import com.subsTracker.subs.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;



    @GetMapping
    public List<Subscription> getAll(@Valid @AuthenticationPrincipal UserDetails userDetails) {
        return subscriptionService.getAllByUser(userDetails.getUsername());
    }


    @GetMapping("/{id}")
    public Subscription getById(@Valid @PathVariable UUID id) {
        return subscriptionService.getById(id);
    }

    @PostMapping
    public Subscription create(@Valid @RequestBody Subscription subscription,
                               @Valid @AuthenticationPrincipal UserDetails userDetails) {
        return subscriptionService.create(subscription, userDetails.getUsername());
    }


    @PutMapping("/{id}")
    public Subscription update(@Valid @PathVariable UUID id,
                               @Valid @RequestBody Subscription subscription,
                               @Valid @AuthenticationPrincipal UserDetails userDetails) {
        return subscriptionService.update(id, subscription, userDetails.getUsername());
    }


    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable UUID id,
                       @Valid @AuthenticationPrincipal UserDetails userDetails) {
        subscriptionService.delete(id, userDetails.getUsername());
    }
}
