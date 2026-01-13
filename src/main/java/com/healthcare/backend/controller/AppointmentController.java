package com.healthcare.backend.controller;

import com.healthcare.backend.entity.AppointmentPatient;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.repository.AppointmentPatientRepository;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppointmentController {

    @Autowired
    private AppointmentPatientRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Create a new appointment
     * POST /api/appointments
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, Object> request) {
        Long patientId = ((Number) request.get("patientId")).longValue();
        Long doctorId = ((Number) request.get("doctorId")).longValue();
        String appointmentType = (String) request.get("appointmentType");
        String appointmentDateStr = (String) request.get("appointmentDate");
        String reason = (String) request.getOrDefault("reason", "");
        String notes = (String) request.getOrDefault("notes", "");
        Integer scheduledDuration = request.containsKey("scheduledDuration") 
            ? ((Number) request.get("scheduledDuration")).intValue() 
            : null;

        if (patientId == null || doctorId == null || appointmentType == null || appointmentDateStr == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "patientId, doctorId, appointmentType, and appointmentDate are required"));
        }

        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);

        if (patientOpt.isEmpty() || doctorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Patient or Doctor not found"));
        }

        try {
            LocalDateTime appointmentDate = LocalDateTime.parse(appointmentDateStr);
            AppointmentPatient appointment = new AppointmentPatient(
                    patientOpt.get(),
                    doctorOpt.get(),
                    appointmentType,
                    appointmentDate
            );
            appointment.setReason(reason);
            appointment.setNotes(notes);
            appointment.setScheduledDuration(scheduledDuration);

            AppointmentPatient saved = appointmentRepository.save(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid appointment date format. Use ISO format: 2026-01-20T10:30:00"));
        }
    }

    /**
     * Get appointment by ID
     * GET /api/appointments/{appointmentId}
     */
    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAppointment(@PathVariable Long appointmentId) {
        Optional<AppointmentPatient> apptOpt = appointmentRepository.findById(appointmentId);
        if (apptOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Appointment not found"));
        }
        return ResponseEntity.ok(mapToResponse(apptOpt.get()));
    }

    /**
     * Get all appointments for a patient
     * GET /api/appointments/patient/{patientId}
     */
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAppointmentsByPatient(@PathVariable Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Patient not found"));
        }

        List<Map<String, Object>> appointments = appointmentRepository.findAll().stream()
                .filter(a -> a.getPatient().getId().equals(patientId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(appointments);
    }

    /**
     * Get all appointments for a doctor
     * GET /api/appointments/doctor/{doctorId}
     */
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Doctor not found"));
        }

        List<Map<String, Object>> appointments = appointmentRepository.findAll().stream()
                .filter(a -> a.getDoctor().getId().equals(doctorId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(appointments);
    }

    /**
     * Update appointment details
     * PUT /api/appointments/{appointmentId}
     */
    @PutMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody Map<String, Object> request) {

        Optional<AppointmentPatient> apptOpt = appointmentRepository.findById(appointmentId);
        if (apptOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Appointment not found"));
        }

        try {
            AppointmentPatient appointment = apptOpt.get();

            if (request.containsKey("appointmentType")) {
                appointment.setAppointmentType((String) request.get("appointmentType"));
            }
            if (request.containsKey("appointmentDate")) {
                appointment.setAppointmentDate(LocalDateTime.parse((String) request.get("appointmentDate")));
            }
            if (request.containsKey("reason")) {
                appointment.setReason((String) request.get("reason"));
            }
            if (request.containsKey("notes")) {
                appointment.setNotes((String) request.get("notes"));
            }
            if (request.containsKey("scheduledDuration")) {
                appointment.setScheduledDuration(((Number) request.get("scheduledDuration")).intValue());
            }

            AppointmentPatient updated = appointmentRepository.save(appointment);
            return ResponseEntity.ok(mapToResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid request data: " + e.getMessage()));
        }
    }

    /**
     * Cancel appointment (soft delete - set status to CANCELLED)
     * DELETE /api/appointments/{appointmentId}
     */
    @DeleteMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long appointmentId) {
        Optional<AppointmentPatient> apptOpt = appointmentRepository.findById(appointmentId);
        if (apptOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Appointment not found"));
        }

        AppointmentPatient appointment = apptOpt.get();
        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);

        return ResponseEntity.noContent().build();
    }

    /**
     * Helper method to convert AppointmentPatient to response Map
     */
    private Map<String, Object> mapToResponse(AppointmentPatient appointment) {
        return Map.ofEntries(
                Map.entry("appointmentId", appointment.getId()),
                Map.entry("patientId", appointment.getPatient().getId()),
                Map.entry("doctorId", appointment.getDoctor().getId()),
                Map.entry("appointmentType", appointment.getAppointmentType()),
                Map.entry("status", appointment.getStatus()),
                Map.entry("appointmentDate", appointment.getAppointmentDate()),
                Map.entry("scheduledDuration", appointment.getScheduledDuration()),
                Map.entry("reason", appointment.getReason()),
                Map.entry("notes", appointment.getNotes()),
                Map.entry("createdAt", appointment.getCreatedAt()),
                Map.entry("updatedAt", appointment.getUpdatedAt())
        );
    }
}
