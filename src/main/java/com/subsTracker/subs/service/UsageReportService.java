package com.subsTracker.subs.service;

import com.subsTracker.subs.model.UsageReport;
import com.subsTracker.subs.model.User;
import com.subsTracker.subs.repository.UsageReportRepository;
import com.subsTracker.subs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsageReportService {
    private final UsageReportRepository repo;
    private final UserRepository userRepo;

    public void saveUsage(String email, String service, int minutes, String source) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UsageReport report = new UsageReport();
        report.setUser(user);
        report.setServiceName(service.toLowerCase());
        report.setTotalMinutes(minutes);
        report.setSource(source);
        repo.save(report);
    }

    public int getMonthlyUsage(String email, String serviceName) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        Integer total = repo.getTotalMinutesForServiceInMonth(user, serviceName.toLowerCase(), startOfMonth);

        return total != null ? total : 0;
    }
}
