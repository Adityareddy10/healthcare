package com.healthcare.backend.repository;

import com.healthcare.backend.entity.MedicalRecord;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatient(Patient patient);
    List<MedicalRecord> findByDoctor(Doctor doctor);
    List<MedicalRecord> findByPatientAndDoctor(Patient patient, Doctor doctor);
    List<MedicalRecord> findByRecordType(String recordType);
    List<MedicalRecord> findByRecordDateAfter(LocalDateTime date);
    List<MedicalRecord> findByRecordDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<MedicalRecord> findByPatientOrderByRecordDateDesc(Patient patient);
}
