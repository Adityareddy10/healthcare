package com.healthcare.backend.repository;

import com.healthcare.backend.entity.AppointmentPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {
}

