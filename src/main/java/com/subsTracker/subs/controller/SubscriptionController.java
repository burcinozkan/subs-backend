package com.subsTracker.subs.controller;

import com.subsTracker.subs.model.Subscription;
import com.subsTracker.subs.model.User;
import com.subsTracker.subs.repository.UserRepository;
import com.subsTracker.subs.service.SubscriptionService;
import com.subsTracker.subs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("user/my")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Subscription> subs = subscriptionService.getSubscriptionsByUser(userDetails.getUsername());
        return ResponseEntity.ok(subs);
    }


    @GetMapping
    public List<Map<String, Serializable>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        return subscriptionService.getAllByUser(userDetails.getUsername())
                .stream()
                .map(s -> {
                    Map<String, Serializable> map = new LinkedHashMap<>();
                    map.put("id", s.getId());
                    map.put("serviceName", s.getServiceName());
                    map.put("monthlyPrice", s.getMonthlyPrice());
                    map.put("nextBillingDate", s.getNextBillingDate());
                    map.put("status", s.getStatus());
                    return map;
                })
                .toList();

    }


    @PostMapping
    public Subscription create(@Valid @RequestBody Subscription subscription,
                               @Valid @AuthenticationPrincipal UserDetails userDetails) {
        return subscriptionService.create(subscription, userDetails.getUsername());
    }


    @PutMapping("/{id}")
    public Subscription update(@Valid @PathVariable long id,
                               @Valid @RequestBody Subscription subscription,
                               @Valid @AuthenticationPrincipal UserDetails userDetails) {
        return subscriptionService.update(id, subscription, userDetails.getUsername());
    }


    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable Long id,
                       @Valid @AuthenticationPrincipal UserDetails userDetails) {
        subscriptionService.delete(id, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Subscription subscription = subscriptionService.getSubscriptionByIdAndUserEmail(id, userDetails.getUsername());
        if (subscription == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscription);
    }
    @GetMapping("/sorted")
    public List<Subscription> getSortedSubscriptions(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "date") String sortBy) {

        User user = userService.findByEmail(userDetails.getUsername());
        List<Subscription> subs = subscriptionService.getSubscriptionsByUser(user.getEmail());

        if (sortBy.equalsIgnoreCase("upcoming")) {
            subs.sort(Comparator.comparing(Subscription::getNextBillingDate));
        } else if (sortBy.equalsIgnoreCase("price")) {
            subs.sort(Comparator.comparing(Subscription::getMonthlyPrice).reversed());
        }

        return subs;
    }

}




