package com.subsTracker.subs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "usage_report")
public class UsageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source; // "ANDROID_USAGE", "MANUAL", vb.

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = false)
    private String serviceName;


    @Column(nullable = false)
    private int totalMinutes;


    @CreationTimestamp
    private LocalDateTime createdAt;
}
