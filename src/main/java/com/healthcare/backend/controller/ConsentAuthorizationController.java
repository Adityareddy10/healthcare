package com.healthcare.backend.controller;

import com.healthcare.backend.service.ConsentAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/consents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConsentAuthorizationController {

    @Autowired
    private ConsentAuthorizationService consentAuthorizationService;

    /**
     * POST /api/consents
     * Create a consent authorization for a patient-doctor relationship
     * Request params: patientId, doctorId
     * Request body: { purpose, durationDays (optional, default 365) }
     */
    @PostMapping
    public ResponseEntity<?> createConsent(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestBody Map<String, Object> consentDTO) {
        Map<String, Object> response = consentAuthorizationService.createConsent(patientId, doctorId, consentDTO);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * GET /api/consents/{consentId}
     * Get a specific consent by ID
     */
    @GetMapping("/{consentId}")
    public ResponseEntity<?> getConsent(@PathVariable Long consentId) {
        Map<String, Object> response = consentAuthorizationService.getConsent(consentId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * GET /api/consents/check
     * Check if consent is active between a patient and doctor
     * Query params: patientId, doctorId
     */
    @GetMapping("/check")
    public ResponseEntity<?> isConsentActive(
            @RequestParam Long patientId,
            @RequestParam Long doctorId) {
        try {
            boolean isActive = consentAuthorizationService.isConsentActive(patientId, doctorId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("isActive", isActive);
            response.put("patientId", patientId);
            response.put("doctorId", doctorId);
            if (!isActive) {
                response.put("message", "No active consent found between patient and doctor");
            } else {
                response.put("message", "Active consent found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error checking consent: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * PUT /api/consents/{consentId}/revoke
     * Revoke a consent
     */
    @PutMapping("/{consentId}/revoke")
    public ResponseEntity<?> revokeConsent(@PathVariable Long consentId) {
        Map<String, Object> response = consentAuthorizationService.revokeConsent(consentId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * GET /api/consents/patient/{patientId}
     * Get all consents for a specific patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getConsentsByPatient(@PathVariable Long patientId) {
        Map<String, Object> response = consentAuthorizationService.getConsentsByPatient(patientId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
