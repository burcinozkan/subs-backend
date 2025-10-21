package com.subsTracker.subs.repository;

import com.subsTracker.subs.model.Subscription;
import com.subsTracker.subs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserEmail(String email);
    List<Subscription> findByUser(User user);
    Optional<Subscription> findByIdAndUser(Long id, User user);
    Optional<Subscription> findByUserEmailAndServiceNameIgnoreCase(String email, String serviceName);



}
