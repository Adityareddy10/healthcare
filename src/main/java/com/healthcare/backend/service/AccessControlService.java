package com.healthcare.backend.service;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.entity.User;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.repository.AccessLogRepository;
import com.healthcare.backend.repository.AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccessControlService {
    
    @Autowired
    private AccessLogRepository accessLogRepository;
    
    @Autowired
    private AuthorizationRepository authorizationRepository;
    
    public AccessLog logAccess(User user, Patient patient, String actionType, 
                               String resourceType, String ipAddress) {
        AccessLog accessLog = new AccessLog(user, patient, actionType, resourceType, ipAddress);
        return accessLogRepository.save(accessLog);
    }
    
    public AccessLog logAccessWithAuthorization(User user, Patient patient, 
                                                 String actionType, String resourceType,
                                                 String ipAddress, Authorization authorization) {
        AccessLog accessLog = new AccessLog(user, patient, actionType, resourceType, ipAddress);
        accessLog.setAuthorization(authorization);
        return accessLogRepository.save(accessLog);
    }
    
    public boolean isAccessAuthorized(User user, Patient patient) {
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            return true;
        }
        
        LocalDateTime now = LocalDateTime.now();
        List<Authorization> authorizations = authorizationRepository.findByPatientAndStatusAndEndDateAfter(
            patient, "ACTIVE", now);
        
        return authorizations.stream()
            .anyMatch(auth -> auth.getStartDate().isBefore(now) && auth.getEndDate().isAfter(now));
    }
    
    public List<AccessLog> getUserAccessLogs(User user) {
        return accessLogRepository.findByUser(user);
    }
    
    public List<AccessLog> getPatientAccessLogs(Patient patient) {
        return accessLogRepository.findByPatient(patient);
    }
    
    public List<AccessLog> getUserPatientAccessLogs(User user, Patient patient) {
        return accessLogRepository.findByUserAndPatient(user, patient);
    }
    
    public List<AccessLog> getAccessLogsInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return accessLogRepository.findByAccessTimeBetween(startDate, endDate);
    }
    
    public List<AccessLog> getAccessLogsByActionType(String actionType) {
        return accessLogRepository.findByActionType(actionType);
    }
    
    public AccessLog updateAccessLogStatus(Long id, String status) {
        Optional<AccessLog> accessLog = accessLogRepository.findById(id);
        if (accessLog.isPresent()) {
            AccessLog log = accessLog.get();
            log.setStatus(status);
            return accessLogRepository.save(log);
        }
        return null;
    }
    
    public void deleteAccessLog(Long id) {
        accessLogRepository.deleteById(id);
    }
    
    public List<AccessLog> getAllAccessLogs() {
        return accessLogRepository.findAll();
    }
}
