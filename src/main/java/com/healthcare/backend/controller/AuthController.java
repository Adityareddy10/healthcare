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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

            // Create user with encoded password
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

            // Create user with encoded password
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

            // Create admin user with encoded password
            User admin = new User(username, passwordEncoder.encode(password), email, "ADMIN");
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

    /**
     * Update own password for authenticated user
     * POST /api/auth/update-password
     * Requires: Authentication header
     */
    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
        try {
            // Get currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Not authenticated"));
            }

            String currentUsername = authentication.getName();
            Optional<User> userOptional = userRepository.findByUsername(currentUsername);
            
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");
            String confirmPassword = request.get("confirmPassword");

            // Validate inputs
            if (oldPassword == null || oldPassword.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Old password is required"));
            }
            if (newPassword == null || newPassword.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "New password is required"));
            }
            if (confirmPassword == null || confirmPassword.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Password confirmation is required"));
            }
            if (!newPassword.equals(confirmPassword)) {
                return ResponseEntity.badRequest().body(Map.of("error", "New passwords do not match"));
            }
            if (newPassword.length() < 6) {
                return ResponseEntity.badRequest().body(Map.of("error", "New password must be at least 6 characters long"));
            }

            User user = userOptional.get();

            // Verify old password
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Old password is incorrect"));
            }

            // Update password
            user.setPassword(passwordEncoder.encode(newPassword));
            User updatedUser = userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "Password updated successfully",
                "userId", updatedUser.getId(),
                "username", updatedUser.getUsername()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update password: " + e.getMessage()));
        }
    }

    /**
     * Update own username for authenticated user
     * POST /api/auth/update-username
     * Requires: Authentication header
     */
    @PostMapping("/update-username")
    public ResponseEntity<?> updateUsername(@RequestBody Map<String, String> request) {
        try {
            // Get currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Not authenticated"));
            }

            String currentUsername = authentication.getName();
            Optional<User> userOptional = userRepository.findByUsername(currentUsername);
            
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            String newUsername = request.get("newUsername");
            String password = request.get("password");

            // Validate inputs
            if (newUsername == null || newUsername.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "New username is required"));
            }
            if (password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Password is required to change username"));
            }

            User user = userOptional.get();

            // Verify password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid password"));
            }

            // Check if new username already exists
            if (!newUsername.equals(currentUsername) && userRepository.existsByUsername(newUsername)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            // Update username
            user.setUsername(newUsername);
            User updatedUser = userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                "message", "Username updated successfully",
                "userId", updatedUser.getId(),
                "oldUsername", currentUsername,
                "newUsername", updatedUser.getUsername()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update username: " + e.getMessage()));
        }
    }

    /**
     * Update own profile (for patients and doctors)
     * POST /api/auth/update-profile
     * Requires: Authentication header
     */
    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> request) {
        try {
            // Get currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Not authenticated"));
            }

            String currentUsername = authentication.getName();
            Optional<User> userOptional = userRepository.findByUsername(currentUsername);
            
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }

            User user = userOptional.get();
            String role = user.getRole();

            if ("PATIENT".equals(role)) {
                // Update patient profile
                Optional<Patient> patientOptional = patientRepository.findByEmail(user.getEmail());
                if (!patientOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Patient profile not found"));
                }

                Patient patient = patientOptional.get();
                
                if (request.containsKey("firstName") && request.get("firstName") != null) {
                    patient.setFirstName(request.get("firstName"));
                }
                if (request.containsKey("lastName") && request.get("lastName") != null) {
                    patient.setLastName(request.get("lastName"));
                }
                if (request.containsKey("phoneNumber") && request.get("phoneNumber") != null) {
                    patient.setPhoneNumber(request.get("phoneNumber"));
                }
                if (request.containsKey("address") && request.get("address") != null) {
                    patient.setAddress(request.get("address"));
                }
                if (request.containsKey("city") && request.get("city") != null) {
                    patient.setCity(request.get("city"));
                }
                if (request.containsKey("state") && request.get("state") != null) {
                    patient.setState(request.get("state"));
                }
                if (request.containsKey("zipCode") && request.get("zipCode") != null) {
                    patient.setZipCode(request.get("zipCode"));
                }

                Patient updatedPatient = patientRepository.save(patient);

                return ResponseEntity.ok(Map.of(
                    "message", "Patient profile updated successfully",
                    "userId", user.getId(),
                    "patientId", updatedPatient.getId(),
                    "firstName", updatedPatient.getFirstName(),
                    "lastName", updatedPatient.getLastName(),
                    "phoneNumber", updatedPatient.getPhoneNumber()
                ));

            } else if ("DOCTOR".equals(role)) {
                // Update doctor profile
                Optional<Doctor> doctorOptional = doctorRepository.findByEmail(user.getEmail());
                if (!doctorOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Doctor profile not found"));
                }

                Doctor doctor = doctorOptional.get();
                
                if (request.containsKey("firstName") && request.get("firstName") != null) {
                    doctor.setFirstName(request.get("firstName"));
                }
                if (request.containsKey("lastName") && request.get("lastName") != null) {
                    doctor.setLastName(request.get("lastName"));
                }
                if (request.containsKey("phoneNumber") && request.get("phoneNumber") != null) {
                    doctor.setPhoneNumber(request.get("phoneNumber"));
                }
                if (request.containsKey("specialization") && request.get("specialization") != null) {
                    doctor.setSpecialization(request.get("specialization"));
                }
                if (request.containsKey("department") && request.get("department") != null) {
                    doctor.setDepartment(request.get("department"));
                }

                Doctor updatedDoctor = doctorRepository.save(doctor);

                return ResponseEntity.ok(Map.of(
                    "message", "Doctor profile updated successfully",
                    "userId", user.getId(),
                    "doctorId", updatedDoctor.getId(),
                    "firstName", updatedDoctor.getFirstName(),
                    "lastName", updatedDoctor.getLastName(),
                    "specialization", updatedDoctor.getSpecialization()
                ));

            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Profile update not supported for this role"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update profile: " + e.getMessage()));
        }
    }
}