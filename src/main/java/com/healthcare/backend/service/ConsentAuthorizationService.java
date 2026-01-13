package com.healthcare.backend.service;

import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.repository.AuthorizationRepository;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class ConsentAuthorizationService {

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Create a consent/authorization for a patient-doctor relationship
     */
    public Map<String, Object> createConsent(Long patientId, Long doctorId, Map<String, Object> consentDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Patient> patient = patientRepository.findById(patientId);
            if (!patient.isPresent()) {
                response.put("success", false);
                response.put("message", "Patient not found with ID: " + patientId);
                return response;
            }

            Optional<Doctor> doctor = doctorRepository.findById(doctorId);
            if (!doctor.isPresent()) {
                response.put("success", false);
                response.put("message", "Doctor not found with ID: " + doctorId);
                return response;
            }

            // Check if consent already exists for this patient-doctor pair
            List<Authorization> existingConsents = authorizationRepository.findByPatientAndDoctor(patient.get(), doctor.get());
            if (!existingConsents.isEmpty()) {
                response.put("success", false);
                response.put("message", "Consent already exists for this patient-doctor pair");
                return response;
            }

            String purpose = consentDTO.get("purpose") != null ? (String) consentDTO.get("purpose") : "General Medical Records Access";
            Integer durationDays = consentDTO.get("durationDays") != null ? (Integer) consentDTO.get("durationDays") : 365;
            
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(durationDays);

            Authorization consent = new Authorization();
            consent.setPatient(patient.get());
            consent.setDoctor(doctor.get());
            consent.setAuthorizationType("CONSENT");
            consent.setPurpose(purpose);
            consent.setStartDate(startDate);
            consent.setEndDate(endDate);
            consent.setStatus("ACTIVE");

            Authorization savedConsent = authorizationRepository.save(consent);

            response.put("success", true);
            response.put("message", "Consent created successfully");
            response.put("consentId", savedConsent.getId());
            response.put("consent", buildConsentResponse(savedConsent));
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating consent: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get a specific consent by ID
     */
    public Map<String, Object> getConsent(Long consentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Authorization> consent = authorizationRepository.findById(consentId);
            if (consent.isPresent()) {
                response.put("success", true);
                response.put("message", "Consent retrieved successfully");
                response.put("consent", buildConsentResponse(consent.get()));
            } else {
                response.put("success", false);
                response.put("message", "Consent not found with ID: " + consentId);
            }
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving consent: " + e.getMessage());
            return response;
        }
    }

    /**
     * Check if consent is active between a patient and doctor
     */
    public boolean isConsentActive(Long patientId, Long doctorId) {
        try {
            Optional<Patient> patient = patientRepository.findById(patientId);
            Optional<Doctor> doctor = doctorRepository.findById(doctorId);

            if (!patient.isPresent() || !doctor.isPresent()) {
                return false;
            }

            List<Authorization> consents = authorizationRepository.findByPatientAndStatusAndEndDateAfter(
                    patient.get(), "ACTIVE", LocalDateTime.now());

            return consents.stream()
                    .anyMatch(c -> c.getDoctor().getId().equals(doctorId));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Revoke a consent
     */
    public Map<String, Object> revokeConsent(Long consentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Authorization> consent = authorizationRepository.findById(consentId);
            if (!consent.isPresent()) {
                response.put("success", false);
                response.put("message", "Consent not found with ID: " + consentId);
                return response;
            }

            Authorization authorization = consent.get();
            authorization.setStatus("REVOKED");
            authorization.setEndDate(LocalDateTime.now());

            Authorization revokedConsent = authorizationRepository.save(authorization);

            response.put("success", true);
            response.put("message", "Consent revoked successfully");
            response.put("consent", buildConsentResponse(revokedConsent));
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error revoking consent: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get all consents for a specific patient
     */
    public Map<String, Object> getConsentsByPatient(Long patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Patient> patient = patientRepository.findById(patientId);
            if (!patient.isPresent()) {
                response.put("success", false);
                response.put("message", "Patient not found with ID: " + patientId);
                return response;
            }

            List<Authorization> consents = authorizationRepository.findByPatient(patient.get());
            
            response.put("success", true);
            response.put("message", "Consents retrieved successfully");
            response.put("count", consents.size());
            response.put("consents", consents.stream()
                    .map(this::buildConsentResponse)
                    .toList());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving consents: " + e.getMessage());
            return response;
        }
    }

    /**
     * Helper method to build consent response
     */
    private Map<String, Object> buildConsentResponse(Authorization authorization) {
        Map<String, Object> consentMap = new HashMap<>();
        consentMap.put("consentId", authorization.getId());
        consentMap.put("patientId", authorization.getPatient().getId());
        consentMap.put("doctorId", authorization.getDoctor().getId());
        consentMap.put("patientName", authorization.getPatient().getFirstName() + " " + authorization.getPatient().getLastName());
        consentMap.put("doctorName", authorization.getDoctor().getSpecialization());
        consentMap.put("purpose", authorization.getPurpose());
        consentMap.put("status", authorization.getStatus());
        consentMap.put("startDate", authorization.getStartDate());
        consentMap.put("endDate", authorization.getEndDate());
        consentMap.put("createdAt", authorization.getCreatedAt());
        consentMap.put("isActive", authorization.getStatus().equals("ACTIVE") && 
                       LocalDateTime.now().isBefore(authorization.getEndDate()));
        return consentMap;
    }
}
