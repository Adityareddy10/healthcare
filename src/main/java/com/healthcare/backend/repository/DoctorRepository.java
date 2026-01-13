package com.healthcare.backend.repository;

import com.healthcare.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByDepartment(String department);
    List<Doctor> findByAvailable(Boolean available);
    boolean existsByEmail(String email);
    boolean existsByLicenseNumber(String licenseNumber);
}
