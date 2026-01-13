package com.healthcare.backend.repository;

import com.healthcare.backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByMedicalRecordNumber(String medicalRecordNumber);
    boolean existsByEmail(String email);
    boolean existsByMedicalRecordNumber(String medicalRecordNumber);
}
