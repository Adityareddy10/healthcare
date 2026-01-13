package com.healthcare.backend.controller;

import com.healthcare.backend.entity.User;
import com.healthcare.backend.entity.Patient;
import com.healthcare.backend.entity.Doctor;
import com.healthcare.backend.repository.UserRepository;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Register a new patient user
     * POST /api/auth/register/patient
     */
    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");
            String firstName = request.get("firstName");
            String lastName = request.get("lastName");
            String dateOfBirthStr = request.get("dateOfBirth");
            String gender = request.get("gender");
            String medicalRecordNumber = request.get("medicalRecordNumber");
            String phoneNumber = request.get("phoneNumber");
            String address = request.get("address");
            String city = request.get("city");
            String state = request.get("state");
            String zipCode = request.get("zipCode");
            String bloodType = request.get("bloodType");

            // Validate required fields
            if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username, password, and email are required"));
            }

            // Check if username already exists
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            // Check if email already exists
            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
            }

            // Create user
            User user = new User(username, password, email, "PATIENT");
            user.setActive(true);
            User savedUser = userRepository.save(user);

            // Create patient record
            Patient patient = new Patient();
            patient.setFirstName(firstName != null ? firstName : "");
            patient.setLastName(lastName != null ? lastName : "");
            patient.setEmail(email);
            patient.setMedicalRecordNumber(medicalRecordNumber != null ? medicalRecordNumber : "MRN-" + System.currentTimeMillis());
            patient.setGender(gender != null ? gender : "");
            patient.setPhoneNumber(phoneNumber != null ? phoneNumber : "");
            patient.setAddress(address != null ? address : "");
            patient.setCity(city != null ? city : "");
            patient.setState(state != null ? state : "");
            patient.setZipCode(zipCode != null ? zipCode : "");
            patient.setBloodType(bloodType != null ? bloodType : "");

            if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
                patient.setDateOfBirth(LocalDate.parse(dateOfBirthStr));
            }

            Patient savedPatient = patientRepository.save(patient);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Patient registered successfully",
                "userId", savedUser.getId(),
                "patientId", savedPatient.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    /**
     * Register a new doctor user
     * POST /api/auth/register/doctor
     */
    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");
            String firstName = request.get("firstName");
            String lastName = request.get("lastName");
            String licenseNumber = request.get("licenseNumber");
            String specialization = request.get("specialization");
            String department = request.get("department");
            String phoneNumber = request.get("phoneNumber");

            // Validate required fields
            if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username, password, and email are required"));
            }

            // Check if username already exists
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            // Check if email already exists
            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
            }

            // Check if license number already exists
            if (licenseNumber != null && !licenseNumber.isEmpty() && doctorRepository.existsByLicenseNumber(licenseNumber)) {
                return ResponseEntity.badRequest().body(Map.of("error", "License number already registered"));
            }

            // Create user
            User user = new User(username, password, email, "DOCTOR");
            user.setActive(true);
            User savedUser = userRepository.save(user);

            // Create doctor record
            Doctor doctor = new Doctor();
            doctor.setFirstName(firstName != null ? firstName : "");
            doctor.setLastName(lastName != null ? lastName : "");
            doctor.setEmail(email);
            doctor.setLicenseNumber(licenseNumber != null ? licenseNumber : "LIC-" + System.currentTimeMillis());
            doctor.setSpecialization(specialization != null ? specialization : "");
            doctor.setDepartment(department != null ? department : "");
            doctor.setPhoneNumber(phoneNumber != null ? phoneNumber : "");
            doctor.setAvailable(true);

            Doctor savedDoctor = doctorRepository.save(doctor);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Doctor registered successfully",
                "userId", savedUser.getId(),
                "doctorId", savedDoctor.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    /**
     * Register a new admin user
     * POST /api/auth/register/admin
     */
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");

            // Validate required fields
            if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username, password, and email are required"));
            }

            // Check if username already exists
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            // Check if email already exists
            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
            }

            // Create admin user
            User admin = new User(username, password, email, "ADMIN");
            admin.setActive(true);
            User savedAdmin = userRepository.save(admin);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Admin registered successfully",
                "userId", savedAdmin.getId(),
                "username", savedAdmin.getUsername(),
                "email", savedAdmin.getEmail(),
                "role", savedAdmin.getRole()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    /**
     * Check if username is available
     * GET /api/auth/check-username/{username}
     */
    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsernameAvailability(@PathVariable String username) {
        try {
            if (username == null || username.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("available", false, "message", "Username cannot be empty"));
            }

            boolean exists = userRepository.existsByUsername(username);
            return ResponseEntity.ok(Map.of(
                "username", username,
                "available", !exists,
                "message", exists ? "Username is already taken" : "Username is available"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error checking username: " + e.getMessage()));
        }
    }
}