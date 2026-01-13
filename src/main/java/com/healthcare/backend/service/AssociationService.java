package com.healthcare.backend.service;

import com.healthcare.backend.entity.PatientDoctorAssociation;
import com.healthcare.backend.entity.AppointmentPatient;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.DoctorRepository;
import com.healthcare.backend.repository.PatientDoctorAssociationRepository;
import com.healthcare.backend.repository.AppointmentPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociationService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientDoctorAssociationRepository associationRepository;

    @Autowired
    private AppointmentPatientRepository appointmentRepository;

    public Optional<PatientDoctorAssociation> createAssociation(Long patientId, Long doctorId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);

        if (patientOpt.isEmpty() || doctorOpt.isEmpty()) {
            return Optional.empty();
        }

        PatientDoctorAssociation association = new PatientDoctorAssociation(
                patientOpt.get(),
                doctorOpt.get(),
                "CARE_TEAM",
                true
        );

        PatientDoctorAssociation saved = associationRepository.save(association);
        return Optional.of(saved);
    }

    public Optional<PatientDoctorAssociation> getAssociationStatus(Long associationId) {
        return associationRepository.findById(associationId);
    }

    public Optional<AppointmentPatient> updateAppointmentStatus(Long appointmentId, String status) {
        Optional<AppointmentPatient> apptOpt = appointmentRepository.findById(appointmentId);
        if (apptOpt.isEmpty()) {
            return Optional.empty();
        }
        AppointmentPatient appt = apptOpt.get();
        appt.setStatus(status);
        AppointmentPatient saved = appointmentRepository.save(appt);
        return Optional.of(saved);
    }
}

