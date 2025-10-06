package com.subsTracker.subs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Service name cannot be empty")
    private String serviceName;

    @NotNull(message = "Monthly price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly price must be greater than 0")
    private BigDecimal monthlyPrice;

    @NotNull(message = "Next billing date is required")
    private LocalDate nextBillingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status { ACTIVE, PAUSED, CANCELLED }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
