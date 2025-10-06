package com.subsTracker.subs.service;

import com.subsTracker.subs.model.Subscription;
import com.subsTracker.subs.model.User;
import com.subsTracker.subs.repository.SubscriptionRepository;
import com.subsTracker.subs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public List<Subscription> getAllByUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return subscriptionRepository.findByUser(user);
    }

    public Subscription getById(UUID id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Subscription create(Subscription subscription, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }

    public Subscription update(UUID id, Subscription newSub, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription existing = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!existing.getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        existing.setServiceName(newSub.getServiceName());
        existing.setMonthlyPrice(newSub.getMonthlyPrice());
        existing.setNextBillingDate(newSub.getNextBillingDate());
        existing.setStatus(newSub.getStatus());

        return subscriptionRepository.save(existing);
    }


    public void delete(UUID id, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!sub.getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        subscriptionRepository.delete(sub);
    }
}
