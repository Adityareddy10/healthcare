package com.healthcare.backend.repository;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    List<AccessLog> findByUser(User user);
    List<AccessLog> findByPatient(Patient patient);
    List<AccessLog> findByUserAndPatient(User user, Patient patient);
    List<AccessLog> findByActionType(String actionType);
    List<AccessLog> findByStatus(String status);
    List<AccessLog> findByAccessTimeAfter(LocalDateTime dateTime);
    List<AccessLog> findByAccessTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<AccessLog> findByResourceType(String resourceType);
    List<AccessLog> findByUserAndAccessTimeAfter(User user, LocalDateTime dateTime);
}
