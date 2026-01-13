package com.healthcare.backend.repository;

import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    List<Authorization> findByPatient(Patient patient);
    List<Authorization> findByDoctor(Doctor doctor);
    List<Authorization> findByPatientAndDoctor(Patient patient, Doctor doctor);
    List<Authorization> findByStatus(String status);
    List<Authorization> findByAuthorizationType(String authorizationType);
    List<Authorization> findByEndDateAfter(LocalDateTime date);
    List<Authorization> findByPatientAndStatusAndEndDateAfter(Patient patient, String status, LocalDateTime date);
}
