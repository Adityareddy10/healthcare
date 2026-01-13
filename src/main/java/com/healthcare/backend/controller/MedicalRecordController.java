package com.healthcare.backend.controller;

import com.healthcare.backend.entity.MedicalRecord;
import com.healthcare.backend.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * POST /api/medical-records
     * Create a new medical record for a patient
     * Request params: patientId, doctorId
     * Request body: MedicalRecord DTO (description, diagnosis, treatment, notes, recordType)
     */
    @PostMapping
    public ResponseEntity<?> createMedicalRecord(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestBody MedicalRecord medicalRecordDTO) {
        Map<String, Object> response = medicalRecordService.createMedicalRecord(patientId, doctorId, medicalRecordDTO);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * GET /api/medical-records/{recordId}
     * Get a specific medical record by ID
     */
    @GetMapping("/{recordId}")
    public ResponseEntity<?> getMedicalRecord(@PathVariable Long recordId) {
        Map<String, Object> response = medicalRecordService.getMedicalRecord(recordId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * GET /api/medical-records/patient/{patientId}
     * Get all medical records for a specific patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getMedicalRecordsByPatient(@PathVariable Long patientId) {
        Map<String, Object> response = medicalRecordService.getMedicalRecordsByPatient(patientId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * PUT /api/medical-records/{recordId}
     * Update an existing medical record
     * Request body: MedicalRecord DTO (any fields to update)
     */
    @PutMapping("/{recordId}")
    public ResponseEntity<?> updateMedicalRecord(
            @PathVariable Long recordId,
            @RequestBody MedicalRecord medicalRecordDTO) {
        Map<String, Object> response = medicalRecordService.updateMedicalRecord(recordId, medicalRecordDTO);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * DELETE /api/medical-records/{recordId}
     * Delete a medical record by ID
     */
    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable Long recordId) {
        Map<String, Object> response = medicalRecordService.deleteMedicalRecord(recordId);
        if ((Boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
