package com.subsTracker.subs.repository;

import com.subsTracker.subs.model.UsageReport;
import com.subsTracker.subs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UsageReportRepository extends JpaRepository<UsageReport, Long> {

    List<UsageReport> findByUser(User user);

    @Query("""
    SELECT SUM(u.totalMinutes)
    FROM UsageReport u
    WHERE u.user = :user
      AND u.serviceName = :serviceName
      AND u.createdAt >= :start
""")
    Integer getTotalMinutesForServiceInMonth(User user, String serviceName, LocalDateTime start);

}
