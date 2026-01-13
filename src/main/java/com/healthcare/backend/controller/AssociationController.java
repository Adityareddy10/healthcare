package com.healthcare.backend.controller;

import com.healthcare.backend.entity.PatientDoctorAssociation;
import com.healthcare.backend.entity.AppointmentPatient;
import com.healthcare.backend.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    /**
     * Create an association between a patient and doctor
     * POST /api/associations
     */
    @PostMapping("/associations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAssociation(@RequestBody Map<String, Long> request) {
        Long patientId = request.get("patientId");
        Long doctorId = request.get("doctorId");

        if (patientId == null || doctorId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "patientId and doctorId are required"));
        }

        Optional<PatientDoctorAssociation> created = associationService.createAssociation(patientId, doctorId);
        if (created.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Patient or Doctor not found"));
        }

        PatientDoctorAssociation assoc = created.get();
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "associationId", assoc.getId(),
                "patientId", assoc.getPatient().getId(),
                "doctorId", assoc.getDoctor().getId(),
                "status", assoc.getStatus(),
                "associationType", assoc.getAssociationType(),
                "isPrimary", assoc.getIsPrimary()
        ));
    }

    /**
     * Get association status
     * GET /api/associations/{associationId}/status
     */
    @GetMapping("/associations/{associationId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAssociationStatus(@PathVariable Long associationId) {
        Optional<PatientDoctorAssociation> assocOpt = associationService.getAssociationStatus(associationId);
        if (assocOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Association not found"));
        }

        PatientDoctorAssociation assoc = assocOpt.get();
        return ResponseEntity.ok(Map.of(
                "associationId", assoc.getId(),
                "patientId", assoc.getPatient().getId(),
                "doctorId", assoc.getDoctor().getId(),
                "status", assoc.getStatus(),
                "associationType", assoc.getAssociationType(),
                "isPrimary", assoc.getIsPrimary(),
                "startDate", assoc.getStartDate(),
                "endDate", assoc.getEndDate()
        ));
    }

    /**
     * Update appointment status
     * PUT /api/appointments/{appointmentId}/status
     */
    @PutMapping("/appointments/{appointmentId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");
        if (status == null || status.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "status is required"));
        }

        Optional<AppointmentPatient> apptOpt = associationService.updateAppointmentStatus(appointmentId, status);
        if (apptOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Appointment not found"));
        }

        AppointmentPatient appt = apptOpt.get();
        return ResponseEntity.ok(Map.of(
                "appointmentId", appt.getId(),
                "patientId", appt.getPatient().getId(),
                "doctorId", appt.getDoctor().getId(),
                "status", appt.getStatus(),
                "appointmentType", appt.getAppointmentType(),
                "appointmentDate", appt.getAppointmentDate()
        ));
    }
}

