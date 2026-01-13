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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserManagementController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create a generic user (admin-managed)
     * POST /api/users
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");
            String role = request.getOrDefault("role", "USER");

            if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username, password, and email are required"));
            }

            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
            }

            User user = new User(username, passwordEncoder.encode(password), email, role.toUpperCase());
            user.setActive(true);
            User savedUser = userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User created successfully",
                "userId", savedUser.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail(),
                "role", savedUser.getRole()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to create user: " + e.getMessage()));
        }
    }

    /**
     * Get all users - Admin only
     * GET /api/users/all
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            List<Map<String, Object>> userList = users.stream().map(user -> {
                Map<String, Object> userMap = new java.util.HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("username", user.getUsername());
                userMap.put("email", user.getEmail());
                userMap.put("role", user.getRole());
                userMap.put("active", user.getActive());
                userMap.put("createdAt", user.getCreatedAt());
                userMap.put("updatedAt", user.getUpdatedAt());
                return userMap;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(userList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to retrieve users: " + e.getMessage()));
        }
    }

    /**
     * Get user details by ID
     * GET /api/users/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            User user = userOptional.get();
            Map<String, Object> userMap = new java.util.HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("username", user.getUsername());
            userMap.put("email", user.getEmail());
            userMap.put("role", user.getRole());
            userMap.put("active", user.getActive());
            userMap.put("createdAt", user.getCreatedAt());
            userMap.put("updatedAt", user.getUpdatedAt());
            return ResponseEntity.ok(userMap);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to retrieve user: " + e.getMessage()));
        }
    }

    /**
     * Get users by type/role - Admin only
     * GET /api/users/type/{userType}
     */
    @GetMapping("/type/{userType}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersByType(@PathVariable String userType) {
        try {
            List<User> users = userRepository.findAll().stream()
                .filter(u -> userType.equalsIgnoreCase(u.getRole()))
                .collect(Collectors.toList());

            List<Map<String, Object>> userList = users.stream().map(user -> {
                Map<String, Object> userMap = new java.util.HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("username", user.getUsername());
                userMap.put("email", user.getEmail());
                userMap.put("role", user.getRole());
                userMap.put("active", user.getActive());
                userMap.put("createdAt", user.getCreatedAt());
                userMap.put("updatedAt", user.getUpdatedAt());
                return userMap;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(userList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to retrieve users: " + e.getMessage()));
        }
    }

    /**
     * Create a new patient - Admin only
     * POST /api/users/create/patient
     */
    @PostMapping("/create/patient")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPatient(@RequestBody Map<String, String> request) {
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
            User user = new User(username, passwordEncoder.encode(password), email, "PATIENT");
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
                "message", "Patient created successfully",
                "userId", savedUser.getId(),
                "patientId", savedPatient.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to create patient: " + e.getMessage()));
        }
    }

    /**
     * Create a new doctor - Admin only
     * POST /api/users/create/doctor
     */
    @PostMapping("/create/doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDoctor(@RequestBody Map<String, String> request) {
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
            User user = new User(username, passwordEncoder.encode(password), email, "DOCTOR");
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
                "message", "Doctor created successfully",
                "userId", savedUser.getId(),
                "doctorId", savedDoctor.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to create doctor: " + e.getMessage()));
        }
    }

    /**
     * Deactivate a user - Admin only
     * PUT /api/users/{userId}/deactivate
     */
    private ResponseEntity<?> handleDeactivateUser(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            User user = userOptional.get();
            user.setActive(false);
            User updatedUser = userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "User deactivated successfully",
                "userId", updatedUser.getId(),
                "username", updatedUser.getUsername(),
                "active", updatedUser.getActive()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to deactivate user: " + e.getMessage()));
        }
    }

    /**
     * Deactivate a user via PUT - Admin only (existing behavior)
     * PUT /api/users/{userId}/deactivate
     */
    @PutMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateUserPut(@PathVariable Long userId) {
        return handleDeactivateUser(userId);
    }

    /**
     * Deactivate a user via PATCH - Admin only (matches spec)
     * PATCH /api/users/{userId}/deactivate
     */
    @PatchMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateUserPatch(@PathVariable Long userId) {
        return handleDeactivateUser(userId);
    }

    /**
     * Activate a user - Admin only
     * PUT /api/users/{userId}/activate
     */
    @PutMapping("/{userId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            User user = userOptional.get();
            user.setActive(true);
            User updatedUser = userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "User activated successfully",
                "userId", updatedUser.getId(),
                "username", updatedUser.getUsername(),
                "active", updatedUser.getActive()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to activate user: " + e.getMessage()));
        }
    }
}