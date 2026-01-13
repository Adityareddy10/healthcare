package com.healthcare.backend.controller;

import com.healthcare.backend.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    /**
     * POST /api/audit-logs
     * Log an access event
     * Request params: userId, patientId, resourceType, resourceId, action
     */
    @PostMapping
    public ResponseEntity<?> logAccess(
            @RequestParam Long userId,
            @RequestParam Long patientId,
            @RequestParam String resourceType,
            @RequestParam Long resourceId,
            @RequestParam String action) {
        Map<String, Object> response = auditLogService.logAccess(userId, patientId, resourceType, resourceId, action);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * GET /api/audit-logs/{logId}
     * Get a specific audit log by ID
     */
    @GetMapping("/{logId}")
    public ResponseEntity<?> getAuditLog(@PathVariable Long logId) {
        Map<String, Object> response = auditLogService.getAuditLog(logId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * GET /api/audit-logs/user/{userId}
     * Get all audit logs for a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLogsByUser(@PathVariable Long userId) {
        Map<String, Object> response = auditLogService.getLogsByUser(userId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * GET /api/audit-logs/resource
     * Get audit logs by resource type and resource ID
     * Query params: resourceType, resourceId
     */
    @GetMapping("/resource")
    public ResponseEntity<?> getLogsByResource(
            @RequestParam String resourceType,
            @RequestParam Long resourceId) {
        Map<String, Object> response = auditLogService.getLogsByResource(resourceType, resourceId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * GET /api/audit-logs
     * Get all audit logs
     */
    @GetMapping
    public ResponseEntity<?> getAllAuditLogs() {
        Map<String, Object> response = auditLogService.getAllAuditLogs();
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
