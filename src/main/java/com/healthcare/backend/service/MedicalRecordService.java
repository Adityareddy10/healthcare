package com.healthcare.backend.service;

import com.healthcare.backend.entity.MedicalRecord;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.repository.MedicalRecordRepository;
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
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Create a new medical record for a patient
     */
    public Map<String, Object> createMedicalRecord(Long patientId, Long doctorId, MedicalRecord medicalRecordDTO) {
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

            MedicalRecord record = new MedicalRecord();
            record.setPatient(patient.get());
            record.setDoctor(doctor.get());
            record.setRecordType(medicalRecordDTO.getRecordType() != null ? medicalRecordDTO.getRecordType() : "General");
            record.setDescription(medicalRecordDTO.getDescription());
            record.setDiagnosis(medicalRecordDTO.getDiagnosis());
            record.setTreatment(medicalRecordDTO.getTreatment());
            record.setNotes(medicalRecordDTO.getNotes());
            record.setRecordDate(LocalDateTime.now());

            MedicalRecord savedRecord = medicalRecordRepository.save(record);

            response.put("success", true);
            response.put("message", "Medical record created successfully");
            response.put("recordId", savedRecord.getId());
            response.put("record", savedRecord);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating medical record: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get a specific medical record by ID
     */
    public Map<String, Object> getMedicalRecord(Long recordId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<MedicalRecord> record = medicalRecordRepository.findById(recordId);
            if (record.isPresent()) {
                response.put("success", true);
                response.put("message", "Medical record retrieved successfully");
                response.put("record", record.get());
            } else {
                response.put("success", false);
                response.put("message", "Medical record not found with ID: " + recordId);
            }
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving medical record: " + e.getMessage());
            return response;
        }
    }

    /**
     * Get all medical records for a specific patient
     */
    public Map<String, Object> getMedicalRecordsByPatient(Long patientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Patient> patient = patientRepository.findById(patientId);
            if (!patient.isPresent()) {
                response.put("success", false);
                response.put("message", "Patient not found with ID: " + patientId);
                return response;
            }

            List<MedicalRecord> records = medicalRecordRepository.findByPatientOrderByRecordDateDesc(patient.get());
            response.put("success", true);
            response.put("message", "Medical records retrieved successfully");
            response.put("count", records.size());
            response.put("records", records);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving medical records: " + e.getMessage());
            return response;
        }
    }

    /**
     * Update an existing medical record
     */
    public Map<String, Object> updateMedicalRecord(Long recordId, MedicalRecord medicalRecordDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<MedicalRecord> existingRecord = medicalRecordRepository.findById(recordId);
            if (!existingRecord.isPresent()) {
                response.put("success", false);
                response.put("message", "Medical record not found with ID: " + recordId);
                return response;
            }

            MedicalRecord record = existingRecord.get();
            if (medicalRecordDTO.getDiagnosis() != null) {
                record.setDiagnosis(medicalRecordDTO.getDiagnosis());
            }
            if (medicalRecordDTO.getDescription() != null) {
                record.setDescription(medicalRecordDTO.getDescription());
            }
            if (medicalRecordDTO.getTreatment() != null) {
                record.setTreatment(medicalRecordDTO.getTreatment());
            }
            if (medicalRecordDTO.getNotes() != null) {
                record.setNotes(medicalRecordDTO.getNotes());
            }

            MedicalRecord updatedRecord = medicalRecordRepository.save(record);

            response.put("success", true);
            response.put("message", "Medical record updated successfully");
            response.put("record", updatedRecord);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating medical record: " + e.getMessage());
            return response;
        }
    }

    /**
     * Delete a medical record by ID
     */
    public Map<String, Object> deleteMedicalRecord(Long recordId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<MedicalRecord> record = medicalRecordRepository.findById(recordId);
            if (!record.isPresent()) {
                response.put("success", false);
                response.put("message", "Medical record not found with ID: " + recordId);
                return response;
            }

            medicalRecordRepository.deleteById(recordId);

            response.put("success", true);
            response.put("message", "Medical record deleted successfully");
            response.put("recordId", recordId);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting medical record: " + e.getMessage());
            return response;
        }
    }
}
