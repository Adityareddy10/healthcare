#!/usr/bin/env python3
"""
Python script to generate all Java files for Spring Boot Healthcare Backend Project
All files are written WITHOUT UTF-8 BOM encoding
"""

import os
from pathlib import Path

# Define base paths
BASE_PATH = r"C:\Users\reddy\Downloads\backend\backend\src\main\java\com\healthcare\backend"
ENTITY_PATH = os.path.join(BASE_PATH, "entity")
REPOSITORY_PATH = os.path.join(BASE_PATH, "repository")
SERVICE_PATH = os.path.join(BASE_PATH, "service")
CONFIG_PATH = os.path.join(BASE_PATH, "config")
CONTROLLER_PATH = os.path.join(BASE_PATH, "controller")

# Create directories if they don't exist
for path in [ENTITY_PATH, REPOSITORY_PATH, SERVICE_PATH, CONFIG_PATH, CONTROLLER_PATH]:
    Path(path).mkdir(parents=True, exist_ok=True)

def write_java_file(filepath, content):
    """Write Java file without BOM encoding"""
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

# ==================== ENTITY CLASSES ====================

user_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String role;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public User() {}
    
    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.active = true;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
'''

patient_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String medicalRecordNumber;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String state;
    
    @Column(nullable = false)
    private String zipCode;
    
    @Column(nullable = false)
    private String bloodType;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Patient() {}
    
    public Patient(String firstName, String lastName, String medicalRecordNumber, 
                   LocalDate dateOfBirth, String gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalRecordNumber = medicalRecordNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }
    
    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
    
    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
'''

doctor_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String licenseNumber;
    
    @Column(nullable = false)
    private String specialization;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private Boolean available;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Doctor() {}
    
    public Doctor(String firstName, String lastName, String licenseNumber, 
                  String specialization, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.email = email;
        this.available = true;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
'''

authorization_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authorizations")
public class Authorization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(nullable = false)
    private String authorizationType;
    
    @Column(nullable = false)
    private String status;
    
    @Column(columnDefinition = "TEXT")
    private String purpose;
    
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Authorization() {}
    
    public Authorization(Patient patient, Doctor doctor, String authorizationType, 
                         LocalDateTime startDate, LocalDateTime endDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.authorizationType = authorizationType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "ACTIVE";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public String getAuthorizationType() {
        return authorizationType;
    }
    
    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
'''

access_log_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
public class AccessLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorization_id")
    private Authorization authorization;
    
    @Column(nullable = false)
    private String actionType;
    
    @Column(nullable = false)
    private String resourceType;
    
    @Column(nullable = false)
    private String ipAddress;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "access_time", nullable = false)
    private LocalDateTime accessTime;
    
    @PrePersist
    protected void onCreate() {
        if (accessTime == null) {
            accessTime = LocalDateTime.now();
        }
    }
    
    // Constructors
    public AccessLog() {}
    
    public AccessLog(User user, Patient patient, String actionType, 
                     String resourceType, String ipAddress) {
        this.user = user;
        this.patient = patient;
        this.actionType = actionType;
        this.resourceType = resourceType;
        this.ipAddress = ipAddress;
        this.status = "SUCCESS";
        this.accessTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Authorization getAuthorization() {
        return authorization;
    }
    
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
    
    public String getActionType() {
        return actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getAccessTime() {
        return accessTime;
    }
    
    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }
}
'''

medical_record_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(nullable = false)
    private String recordType;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String diagnosis;
    
    @Column(columnDefinition = "TEXT")
    private String treatment;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (recordDate == null) {
            recordDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public MedicalRecord() {}
    
    public MedicalRecord(Patient patient, Doctor doctor, String recordType, 
                         String description) {
        this.patient = patient;
        this.doctor = doctor;
        this.recordType = recordType;
        this.description = description;
        this.recordDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public String getRecordType() {
        return recordType;
    }
    
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getRecordDate() {
        return recordDate;
    }
    
    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
'''

patient_doctor_association_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_doctor_associations")
public class PatientDoctorAssociation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(nullable = false)
    private String associationType;
    
    @Column(nullable = false)
    private Boolean isPrimary;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public PatientDoctorAssociation() {}
    
    public PatientDoctorAssociation(Patient patient, Doctor doctor, 
                                     String associationType, Boolean isPrimary) {
        this.patient = patient;
        this.doctor = doctor;
        this.associationType = associationType;
        this.isPrimary = isPrimary;
        this.status = "ACTIVE";
        this.startDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public String getAssociationType() {
        return associationType;
    }
    
    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }
    
    public Boolean getIsPrimary() {
        return isPrimary;
    }
    
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
'''

appointment_patient_java = '''package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_patients")
public class AppointmentPatient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(nullable = false)
    private String appointmentType;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;
    
    @Column(name = "scheduled_duration")
    private Integer scheduledDuration;
    
    @Column(columnDefinition = "TEXT")
    private String reason;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public AppointmentPatient() {}
    
    public AppointmentPatient(Patient patient, Doctor doctor, String appointmentType,
                              LocalDateTime appointmentDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentType = appointmentType;
        this.appointmentDate = appointmentDate;
        this.status = "SCHEDULED";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public String getAppointmentType() {
        return appointmentType;
    }
    
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    public Integer getScheduledDuration() {
        return scheduledDuration;
    }
    
    public void setScheduledDuration(Integer scheduledDuration) {
        this.scheduledDuration = scheduledDuration;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
'''

# ==================== REPOSITORY CLASSES ====================

user_repository_java = '''package com.healthcare.backend.repository;

import com.healthcare.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
'''

patient_repository_java = '''package com.healthcare.backend.repository;

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
'''

doctor_repository_java = '''package com.healthcare.backend.repository;

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
'''

authorization_repository_java = '''package com.healthcare.backend.repository;

import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
'''

access_log_repository_java = '''package com.healthcare.backend.repository;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    List<AccessLog> findByUser(User user);
    List<AccessLog> findByPatient(Patient patient);
    List<AccessLog> findByUserAndPatient(User user, Patient patient);
    List<AccessLog> findByActionType(String actionType);
    List<AccessLog> findByStatus(String status);
    List<AccessLog> findByAccessTimeAfter(LocalDateTime dateTime);
    List<AccessLog> findByAccessTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<AccessLog> findByResourceType(String resourceType);
    List<AccessLog> findByUserAndAccessTimeAfter(User user, LocalDateTime dateTime);
}
'''

medical_record_repository_java = '''package com.healthcare.backend.repository;

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
'''

# ==================== SERVICE CLASSES ====================

user_service_java = '''package com.healthcare.backend.service;

import com.healthcare.backend.entity.User;
import com.healthcare.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPassword() != null) {
                existingUser.setPassword(userDetails.getPassword());
            }
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }
            if (userDetails.getActive() != null) {
                existingUser.setActive(userDetails.getActive());
            }
            return userRepository.save(existingUser);
        }
        return null;
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public void deactivateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setActive(false);
            userRepository.save(existingUser);
        }
    }
    
    public void activateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setActive(true);
            userRepository.save(existingUser);
        }
    }
}
'''

authorization_service_java = '''package com.healthcare.backend.service;

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
'''

access_control_service_java = '''package com.healthcare.backend.service;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.entity.User;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.repository.AccessLogRepository;
import com.healthcare.backend.repository.AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccessControlService {
    
    @Autowired
    private AccessLogRepository accessLogRepository;
    
    @Autowired
    private AuthorizationRepository authorizationRepository;
    
    public AccessLog logAccess(User user, Patient patient, String actionType, 
                               String resourceType, String ipAddress) {
        AccessLog accessLog = new AccessLog(user, patient, actionType, resourceType, ipAddress);
        return accessLogRepository.save(accessLog);
    }
    
    public AccessLog logAccessWithAuthorization(User user, Patient patient, 
                                                 String actionType, String resourceType,
                                                 String ipAddress, Authorization authorization) {
        AccessLog accessLog = new AccessLog(user, patient, actionType, resourceType, ipAddress);
        accessLog.setAuthorization(authorization);
        return accessLogRepository.save(accessLog);
    }
    
    public boolean isAccessAuthorized(User user, Patient patient) {
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            return true;
        }
        
        LocalDateTime now = LocalDateTime.now();
        List<Authorization> authorizations = authorizationRepository.findByPatientAndStatusAndEndDateAfter(
            patient, "ACTIVE", now);
        
        return authorizations.stream()
            .anyMatch(auth -> auth.getStartDate().isBefore(now) && auth.getEndDate().isAfter(now));
    }
    
    public List<AccessLog> getUserAccessLogs(User user) {
        return accessLogRepository.findByUser(user);
    }
    
    public List<AccessLog> getPatientAccessLogs(Patient patient) {
        return accessLogRepository.findByPatient(patient);
    }
    
    public List<AccessLog> getUserPatientAccessLogs(User user, Patient patient) {
        return accessLogRepository.findByUserAndPatient(user, patient);
    }
    
    public List<AccessLog> getAccessLogsInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return accessLogRepository.findByAccessTimeBetween(startDate, endDate);
    }
    
    public List<AccessLog> getAccessLogsByActionType(String actionType) {
        return accessLogRepository.findByActionType(actionType);
    }
    
    public AccessLog updateAccessLogStatus(Long id, String status) {
        Optional<AccessLog> accessLog = accessLogRepository.findById(id);
        if (accessLog.isPresent()) {
            AccessLog log = accessLog.get();
            log.setStatus(status);
            return accessLogRepository.save(log);
        }
        return null;
    }
    
    public void deleteAccessLog(Long id) {
        accessLogRepository.deleteById(id);
    }
    
    public List<AccessLog> getAllAccessLogs() {
        return accessLogRepository.findAll();
    }
}
'''

# ==================== CONFIG CLASS ====================

security_config_java = '''package com.healthcare.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false)
            .maxAge(3600);
    }
}
'''

# ==================== CONTROLLER CLASSES ====================

authorization_controller_java = '''package com.healthcare.backend.controller;

import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/authorizations")
public class AuthorizationController {
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @PostMapping
    public ResponseEntity<Authorization> createAuthorization(@RequestBody Authorization authorization) {
        try {
            Authorization createdAuthorization = authorizationService.createAuthorization(authorization);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthorization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Authorization> getAuthorization(@PathVariable Long id) {
        Optional<Authorization> authorization = authorizationService.getAuthorizationById(id);
        if (authorization.isPresent()) {
            return ResponseEntity.ok(authorization.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Authorization>> getAllAuthorizations() {
        List<Authorization> authorizations = authorizationService.getAllAuthorizations();
        return ResponseEntity.ok(authorizations);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Authorization>> getAuthorizationsByPatient(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Authorization>> getAuthorizationsByDoctor(@PathVariable Long doctorId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Authorization> updateAuthorization(@PathVariable Long id, 
                                                              @RequestBody Authorization authorizationDetails) {
        try {
            Optional<Authorization> authorization = authorizationService.getAuthorizationById(id);
            if (authorization.isPresent()) {
                Authorization auth = authorization.get();
                if (authorizationDetails.getStatus() != null) {
                    auth.setStatus(authorizationDetails.getStatus());
                }
                if (authorizationDetails.getEndDate() != null) {
                    auth.setEndDate(authorizationDetails.getEndDate());
                }
                if (authorizationDetails.getPurpose() != null) {
                    auth.setPurpose(authorizationDetails.getPurpose());
                }
                Authorization updatedAuthorization = authorizationService.createAuthorization(auth);
                return ResponseEntity.ok(updatedAuthorization);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorization(@PathVariable Long id) {
        try {
            authorizationService.deleteAuthorization(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}/revoke")
    public ResponseEntity<Authorization> revokeAuthorization(@PathVariable Long id) {
        try {
            Authorization revokedAuthorization = authorizationService.revokeAuthorization(id);
            if (revokedAuthorization != null) {
                return ResponseEntity.ok(revokedAuthorization);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
'''

access_log_controller_java = '''package com.healthcare.backend.controller;

import com.healthcare.backend.entity.AccessLog;
import com.healthcare.backend.service.AccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {
    
    @Autowired
    private AccessControlService accessControlService;
    
    @GetMapping
    public ResponseEntity<List<AccessLog>> getAllAccessLogs() {
        List<AccessLog> accessLogs = accessControlService.getAllAccessLogs();
        return ResponseEntity.ok(accessLogs);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccessLog>> getUserAccessLogs(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AccessLog>> getPatientAccessLogs(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<AccessLog>> getAccessLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<AccessLog> accessLogs = accessControlService.getAccessLogsInDateRange(startDate, endDate);
            return ResponseEntity.ok(accessLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/action/{actionType}")
    public ResponseEntity<List<AccessLog>> getAccessLogsByActionType(@PathVariable String actionType) {
        try {
            List<AccessLog> accessLogs = accessControlService.getAccessLogsByActionType(actionType);
            return ResponseEntity.ok(accessLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<AccessLog> createAccessLog(@RequestBody AccessLog accessLog) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<AccessLog> updateAccessLogStatus(@PathVariable Long id,
                                                           @RequestParam String status) {
        try {
            AccessLog updatedAccessLog = accessControlService.updateAccessLogStatus(id, status);
            if (updatedAccessLog != null) {
                return ResponseEntity.ok(updatedAccessLog);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessLog(@PathVariable Long id) {
        try {
            accessControlService.deleteAccessLog(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
'''

# ==================== FILE CREATION ====================

def main():
    files_to_create = [
        # Entity files
        (os.path.join(ENTITY_PATH, "User.java"), user_java),
        (os.path.join(ENTITY_PATH, "Patient.java"), patient_java),
        (os.path.join(ENTITY_PATH, "Doctor.java"), doctor_java),
        (os.path.join(ENTITY_PATH, "Authorization.java"), authorization_java),
        (os.path.join(ENTITY_PATH, "AccessLog.java"), access_log_java),
        (os.path.join(ENTITY_PATH, "MedicalRecord.java"), medical_record_java),
        (os.path.join(ENTITY_PATH, "PatientDoctorAssociation.java"), patient_doctor_association_java),
        (os.path.join(ENTITY_PATH, "AppointmentPatient.java"), appointment_patient_java),
        
        # Repository files
        (os.path.join(REPOSITORY_PATH, "UserRepository.java"), user_repository_java),
        (os.path.join(REPOSITORY_PATH, "PatientRepository.java"), patient_repository_java),
        (os.path.join(REPOSITORY_PATH, "DoctorRepository.java"), doctor_repository_java),
        (os.path.join(REPOSITORY_PATH, "AuthorizationRepository.java"), authorization_repository_java),
        (os.path.join(REPOSITORY_PATH, "AccessLogRepository.java"), access_log_repository_java),
        (os.path.join(REPOSITORY_PATH, "MedicalRecordRepository.java"), medical_record_repository_java),
        
        # Service files
        (os.path.join(SERVICE_PATH, "UserService.java"), user_service_java),
        (os.path.join(SERVICE_PATH, "AuthorizationService.java"), authorization_service_java),
        (os.path.join(SERVICE_PATH, "AccessControlService.java"), access_control_service_java),
        
        # Config files
        (os.path.join(CONFIG_PATH, "SecurityConfig.java"), security_config_java),
        
        # Controller files
        (os.path.join(CONTROLLER_PATH, "AuthorizationController.java"), authorization_controller_java),
        (os.path.join(CONTROLLER_PATH, "AccessLogController.java"), access_log_controller_java),
    ]
    
    created_count = 0
    for filepath, content in files_to_create:
        try:
            write_java_file(filepath, content)
            created_count += 1
            print(f"✓ Created: {filepath}")
        except Exception as e:
            print(f"✗ Failed to create {filepath}: {str(e)}")
    
    print(f"\n{'='*60}")
    print(f"Summary: {created_count}/{len(files_to_create)} files created successfully")
    print(f"{'='*60}")

if __name__ == "__main__":
    main()
