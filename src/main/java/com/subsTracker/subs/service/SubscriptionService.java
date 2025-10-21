package com.subsTracker.subs.service;

import com.subsTracker.subs.exception.DuplicateSubscriptionException;
import com.subsTracker.subs.exception.GlobalExceptionHandler.*;
import com.subsTracker.subs.model.Subscription;
import com.subsTracker.subs.model.User;
import com.subsTracker.subs.repository.SubscriptionRepository;
import com.subsTracker.subs.repository.UserRepository;
import com.subsTracker.subs.util.IconMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UsageReportService usageReportService;

    @Cacheable(value = "subscription", key = "#username")
    public List<Subscription> getAllByUser(String username) {
        log.info(" Cache MISS - Veritabanından subscription'ları çekiliyor: {}", username);
        List<Subscription> subscriptions = subscriptionRepository.findByUserEmail(username);
        log.info(" {} kullanıcısı için {} subscription bulundu", username, subscriptions.size());
        return subscriptions;
    }



    public Subscription getById(long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Transactional
    public Subscription create(Subscription subscription, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String normalizedServiceName = subscription.getServiceName().trim().toLowerCase();


        Optional<Subscription> existing = subscriptionRepository
                .findByUserEmailAndServiceNameIgnoreCase(user.getEmail(), normalizedServiceName);

        if (existing.isPresent()) {
            log.warn("Duplicate attempt: {} already exists for {}", normalizedServiceName, username);
            throw new RuntimeException("This subscription already exists for this user");
        }

        subscription.setServiceName(normalizedServiceName);
        subscription.setUser(user);
        subscription.setIconUrl(IconMapper.getIconUrl(normalizedServiceName));


        Subscription saved = subscriptionRepository.save(subscription);
        log.info("New subscription saved for {}: {}", username, normalizedServiceName);

        return saved;
    }




    public Subscription update(long id, Subscription newSub, String username) {
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


    public void delete(long id, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!sub.getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        subscriptionRepository.delete(sub);
    }
    public List<Subscription> getSubscriptionsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Subscription> subscriptions = subscriptionRepository.findByUser(user);

        // Her abonelik için kullanım süresi (dakika) hesapla
        for (Subscription s : subscriptions) {
            int mins = usageReportService.getMonthlyUsage(email, s.getServiceName());
            s.setUsageMinutes(mins);
        }

        return subscriptions;
    }

    public Subscription getSubscriptionByIdAndUserEmail(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return subscriptionRepository.findByIdAndUser(id, user).orElse(null);
    }
    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

}
