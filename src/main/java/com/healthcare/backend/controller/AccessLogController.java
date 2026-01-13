package com.healthcare.backend.controller;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.service.AccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {
    
    @Autowired
    private AccessControlService accessControlService;
    
    @GetMapping
    public ResponseEntity<List<AccessLog>> getAllAccessLogs() {
        List<AccessLog> accessLogs = accessControlService.getAllAccessLogs();
        return ResponseEntity.ok(accessLogs);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccessLog>> getUserAccessLogs(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AccessLog>> getPatientAccessLogs(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<AccessLog>> getAccessLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<AccessLog> accessLogs = accessControlService.getAccessLogsInDateRange(startDate, endDate);
            return ResponseEntity.ok(accessLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/action/{actionType}")
    public ResponseEntity<List<AccessLog>> getAccessLogsByActionType(@PathVariable String actionType) {
        try {
            List<AccessLog> accessLogs = accessControlService.getAccessLogsByActionType(actionType);
            return ResponseEntity.ok(accessLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<AccessLog> createAccessLog(@RequestBody AccessLog accessLog) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<AccessLog> updateAccessLogStatus(@PathVariable Long id,
                                                           @RequestParam String status) {
        try {
            AccessLog updatedAccessLog = accessControlService.updateAccessLogStatus(id, status);
            if (updatedAccessLog != null) {
                return ResponseEntity.ok(updatedAccessLog);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessLog(@PathVariable Long id) {
        try {
            accessControlService.deleteAccessLog(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
