package com.healthcare.backend.repository;

import com.healthcare.backend.entity.PatientDoctorAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDoctorAssociationRepository extends JpaRepository<PatientDoctorAssociation, Long> {
}

