package com.subsTracker.subs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@Table(
        name = "subscription",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "service_name"})
        })
public class Subscription  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Service name cannot be empty")
    private String serviceName;

    @Transient
    private int usageMinutes;


    @NotNull(message = "Monthly price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly price must be greater than 0")
    private BigDecimal monthlyPrice;

    @NotNull(message = "Next billing date is required")
    private LocalDate nextBillingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status { ACTIVE, PAUSED, CANCELLED }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "icon_url")
    private String iconUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public enum Category {
        STREAMING,
        MUSIC,
        CLOUD_STORAGE,
        SOFTWARE,
        GAMES,
        EDUCATION,
        FITNESS,
        ENTERTAINMENT,
        OTHER
    }


}
