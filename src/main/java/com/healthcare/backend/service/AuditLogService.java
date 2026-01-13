package com.healthcare.backend.service;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.entity.User;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.repository.AccessLogRepository;
import com.healthcare.backend.repository.UserRepository;
import com.healthcare.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class AuditLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Log an access event
     */
    public Map<String, Object> logAccess(Long userId, Long patientId, String resourceType, Long resourceId, String action) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                response.put("success", false);
                response.put("message", "User not found with ID: " + userId);
                return response;
            }

            Optional<Patient> patient = patientRepository.findById(patientId);
            if (!patient.isPresent()) {
                response.put("success", false);
                response.put("message", "Patient not found with ID: " + patientId);
                return response;
            }

            AccessLog log = new AccessLog();
            log.setUser(user.get());
            log.setPatient(patient.get());
            log.setActionType(action);
            log.setResourceType(resourceType);
            log.setStatus("SUCCESS");
            log.setAccessTime(LocalDateTime.now());
            log.setIpAddress("127.0.0.1");
            log.setDetails("Resource ID: " + resourceId);

            AccessLog savedLog = accessLogRepository.save(log);

            response.put("success", true);
            response.put("message", "Access logged successfully");
            response.put("logId", savedLog.getId());
            response.put("log", buildAuditLogResponse(savedLog));
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error logging access: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get a specific audit log by ID
     */
    public Map<String, Object> getAuditLog(Long logId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<AccessLog> log = accessLogRepository.findById(logId);
            if (log.isPresent()) {
                response.put("success", true);
                response.put("message", "Audit log retrieved successfully");
                response.put("log", buildAuditLogResponse(log.get()));
            } else {
                response.put("success", false);
                response.put("message", "Audit log not found with ID: " + logId);
            }
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving audit log: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get all audit logs for a specific user
     */
    public Map<String, Object> getLogsByUser(Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                response.put("success", false);
                response.put("message", "User not found with ID: " + userId);
                return response;
            }

            List<AccessLog> logs = accessLogRepository.findByUser(user.get());
            response.put("success", true);
            response.put("message", "Audit logs retrieved successfully");
            response.put("count", logs.size());
            response.put("logs", logs.stream()
                    .map(this::buildAuditLogResponse)
                    .toList());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving audit logs: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get audit logs by resource type and resource ID
     */
    public Map<String, Object> getLogsByResource(String resourceType, Long resourceId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AccessLog> logs = accessLogRepository.findByResourceType(resourceType);
            
            // Filter by resource ID from details field
            List<AccessLog> filteredLogs = logs.stream()
                    .filter(log -> log.getDetails() != null && log.getDetails().contains("Resource ID: " + resourceId))
                    .toList();

            response.put("success", true);
            response.put("message", "Audit logs retrieved successfully");
            response.put("count", filteredLogs.size());
            response.put("resourceType", resourceType);
            response.put("resourceId", resourceId);
            response.put("logs", filteredLogs.stream()
                    .map(this::buildAuditLogResponse)
                    .toList());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving audit logs: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get all audit logs
     */
    public Map<String, Object> getAllAuditLogs() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AccessLog> logs = accessLogRepository.findAll();
            response.put("success", true);
            response.put("message", "All audit logs retrieved successfully");
            response.put("count", logs.size());
            response.put("logs", logs.stream()
                    .map(this::buildAuditLogResponse)
                    .toList());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving audit logs: " + e.getMessage());
            return response;
        }
    }

    /**
     * Helper method to build audit log response
     */
    private Map<String, Object> buildAuditLogResponse(AccessLog log) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("logId", log.getId());
        logMap.put("userId", log.getUser().getId());
        logMap.put("userName", log.getUser().getUsername());
        logMap.put("actionType", log.getActionType());
        logMap.put("resourceType", log.getResourceType());
        logMap.put("status", log.getStatus());
        logMap.put("ipAddress", log.getIpAddress());
        logMap.put("accessTime", log.getAccessTime());
        logMap.put("details", log.getDetails());
        if (log.getPatient() != null) {
            logMap.put("patientId", log.getPatient().getId());
            logMap.put("patientName", log.getPatient().getFirstName() + " " + log.getPatient().getLastName());
        }
        return logMap;
    }
}
