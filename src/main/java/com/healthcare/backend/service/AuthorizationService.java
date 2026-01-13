package com.healthcare.backend.service;

import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.repository.AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class AuthorizationService {
    
    @Autowired
    private AuthorizationRepository authorizationRepository;
    
    public Authorization createAuthorization(Authorization authorization) {
        return authorizationRepository.save(authorization);
    }
    
    public Optional<Authorization> getAuthorizationById(Long id) {
        return authorizationRepository.findById(id);
    }
    
    public List<Authorization> getAuthorizationsByPatient(Patient patient) {
        return authorizationRepository.findByPatient(patient);
    }
    
    public List<Authorization> getAuthorizationsByDoctor(Doctor doctor) {
        return authorizationRepository.findByDoctor(doctor);
    }
    
    public List<Authorization> getAuthorizationsByPatientAndDoctor(Patient patient, Doctor doctor) {
        return authorizationRepository.findByPatientAndDoctor(patient, doctor);
    }
    
    public List<Authorization> getActiveAuthorizations(Patient patient) {
        LocalDateTime now = LocalDateTime.now();
        return authorizationRepository.findByPatientAndStatusAndEndDateAfter(patient, "ACTIVE", now);
    }
    
    public boolean hasValidAuthorization(Patient patient, Doctor doctor) {
        LocalDateTime now = LocalDateTime.now();
        List<Authorization> authorizations = authorizationRepository.findByPatientAndDoctor(patient, doctor);
        return authorizations.stream()
            .anyMatch(auth -> auth.getStatus().equals("ACTIVE") && 
                             auth.getEndDate().isAfter(now) &&
                             auth.getStartDate().isBefore(now));
    }
    
    public Authorization revokeAuthorization(Long id) {
        Optional<Authorization> authorization = authorizationRepository.findById(id);
        if (authorization.isPresent()) {
            Authorization auth = authorization.get();
            auth.setStatus("REVOKED");
            return authorizationRepository.save(auth);
        }
        return null;
    }
    
    public void deleteAuthorization(Long id) {
        authorizationRepository.deleteById(id);
    }
    
    public List<Authorization> getAllAuthorizations() {
        return authorizationRepository.findAll();
    }
}
