# Healthcare Management System - Test Cases

## Project Overview
- **Backend**: Spring Boot REST API on port 8081
- **Frontend**: HTML/CSS/JavaScript Dashboard
- **Database**: MySQL
- **Authentication**: Basic Auth (admin:admin123)

---

## User Stories & Test Coverage Summary

| # | User Story | Description | Test Count | Test IDs | Coverage |
|---|-----------|-------------|-----------|----------|----------|
| US-001 | User Authentication | As a user, I want to securely log in with credentials | 6 | AUTH-001 to AUTH-006 | ⬜ |
| US-002 | Appointment Management | As a doctor, I want to create, view, and manage patient appointments | 12 | APT-001 to APT-012 | ⬜ |
| US-003 | Medical Records | As a doctor, I want to create and maintain patient medical records | 10 | MED-001 to MED-010 | ⬜ |
| US-004 | User Management | As an admin, I want to manage system users and their roles | 10 | USER-001 to USER-010 | ⬜ |
| US-005 | Audit Logging | As an admin, I want to track all system activities for compliance | 10 | AUD-001 to AUD-010 | ⬜ |
| US-006 | Consent Management | As a patient, I want to grant/revoke consent for data access | 7 | CONSENT-001 to CONSENT-007 | ⬜ |
| US-007 | Dashboard Interface | As a user, I want an intuitive web interface to access all features | 20 | FE-001 to FE-020 | ⬜ |
| US-008 | Data Persistence | As a user, I want all data to be safely stored in the database | 10 | DB-001 to DB-010 | ⬜ |
| US-009 | API Security | As a user, I want my data to be protected from unauthorized access | 5 | SEC-001 to SEC-005 | ⬜ |
| US-010 | System Performance | As a user, I want the system to respond quickly under load | 5 | PERF-001 to PERF-005 | ⬜ |
| | **TOTAL** | | **95** | | |

---

---

## Test Case Documentation

### Legend
- ✅ = Passed
- ❌ = Failed
- ⏳ = In Progress
- ⬜ = Not Tested

---

## 1. AUTHENTICATION & LOGIN TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| AUTH-001 | Login with valid credentials | /index.html | GET | Username: admin, Password: admin123 | Should redirect to dashboard.html | | ⬜ |
| AUTH-002 | Login with invalid username | /index.html | GET | Username: invalid, Password: admin123 | Should show error message | | ⬜ |
| AUTH-003 | Login with invalid password | /index.html | GET | Username: admin, Password: wrong | Should show error message | | ⬜ |
| AUTH-004 | Check auth header on API call | /api/auth/check-username/admin | GET | Basic Auth header with admin:admin123 | Should return 200 OK | | ⬜ |
| AUTH-005 | Access protected API without auth | /api/appointments | GET | No Authorization header | Should return 401 Unauthorized | | ⬜ |
| AUTH-006 | Logout functionality | Dashboard | Click Logout | Valid session | Should clear session and redirect to login | | ⬜ |

---

## 2. APPOINTMENT MANAGEMENT TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| APT-001 | Create appointment | /api/appointments | POST | patientId=1, doctorId=1, appointmentType=Consultation, appointmentDate=2026-01-20T10:30:00 | Should return 201 Created with appointment ID | | ⬜ |
| APT-002 | Get all appointments | /api/appointments | GET | None | Should return list of all appointments | | ⬜ |
| APT-003 | Get appointment by ID | /api/appointments/1 | GET | appointmentId=1 | Should return appointment details | | ⬜ |
| APT-004 | Get appointments by patient | /api/appointments/patient/1 | GET | patientId=1 | Should return appointments for patient 1 | | ⬜ |
| APT-005 | Get appointments by doctor | /api/appointments/doctor/1 | GET | doctorId=1 | Should return appointments for doctor 1 | | ⬜ |
| APT-006 | Update appointment details | /api/appointments/1 | PUT | appointmentType=Follow-up, appointmentDate=2026-01-21T14:00:00 | Should return updated appointment | | ⬜ |
| APT-007 | Cancel appointment | /api/appointments/1 | DELETE | appointmentId=1 | Should set status to CANCELLED | | ⬜ |
| APT-008 | Create appointment with missing patient | /api/appointments | POST | patientId=999, doctorId=1 | Should return 404 Not Found | | ⬜ |
| APT-009 | Create appointment with missing doctor | /api/appointments | POST | patientId=1, doctorId=999 | Should return 404 Not Found | | ⬜ |
| APT-010 | Verify appointment in database | MySQL | SELECT | SELECT * FROM appointment_patients | Should show created appointment | | ⬜ |
| APT-011 | Create appointment via frontend | Dashboard | Click "New Appointment" | Fill form and submit | Should create and display in table | | ⬜ |
| APT-012 | Delete appointment via frontend | Dashboard | Click Delete button | Confirm deletion | Should remove from table and database | | ⬜ |

---

## 3. MEDICAL RECORDS TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| MED-001 | Create medical record | /api/medical-records | POST | patientId=1, doctorId=1, diagnosis=Cold, treatment=Rest | Should return 201 Created | | ⬜ |
| MED-002 | Get all medical records | /api/medical-records | GET | None | Should return list of all records | | ⬜ |
| MED-003 | Get specific medical record | /api/medical-records/1 | GET | recordId=1 | Should return record details | | ⬜ |
| MED-004 | Get records by patient | /api/medical-records/patient/1 | GET | patientId=1 | Should return patient's medical records | | ⬜ |
| MED-005 | Get records by doctor | /api/medical-records/doctor/1 | GET | doctorId=1 | Should return doctor's patient records | | ⬜ |
| MED-006 | Update medical record | /api/medical-records/1 | PUT | diagnosis=Updated diagnosis | Should return updated record | | ⬜ |
| MED-007 | Delete medical record | /api/medical-records/1 | DELETE | recordId=1 | Should soft delete record | | ⬜ |
| MED-008 | Verify record in database | MySQL | SELECT | SELECT * FROM medical_records | Should show created record | | ⬜ |
| MED-009 | Create record with invalid patient | /api/medical-records | POST | patientId=999, doctorId=1 | Should return 404 Not Found | | ⬜ |
| MED-010 | View records on frontend | Dashboard | Click Medical Records | Load records | Should display all records in table | | ⬜ |

---

## 4. USER MANAGEMENT TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| USER-001 | Create user | /api/users | POST | username=testuser, password=pass123, email=test@example.com, role=USER | Should return 201 Created | | ⬜ |
| USER-002 | Get all users | /api/users | GET | None | Should return list of all users | | ⬜ |
| USER-003 | Get user by ID | /api/users/1 | GET | userId=1 | Should return user details | | ⬜ |
| USER-004 | Check username availability | /api/auth/check-username/admin | GET | username=admin | Should return 200 (exists) | | ⬜ |
| USER-005 | Update user | /api/users/1 | PUT | email=newemail@example.com | Should return updated user | | ⬜ |
| USER-006 | Delete user | /api/users/1 | DELETE | userId=1 | Should delete user | | ⬜ |
| USER-007 | Create duplicate user | /api/users | POST | username=admin (existing) | Should return error | | ⬜ |
| USER-008 | Create user with invalid email | /api/users | POST | username=test, email=invalid | Should return error or create anyway | | ⬜ |
| USER-009 | Verify user in database | MySQL | SELECT | SELECT * FROM users WHERE username='testuser' | Should show created user | | ⬜ |
| USER-010 | View users on frontend | Dashboard | Click Users | Load users | Should display all users in table | | ⬜ |

---

## 5. AUDIT LOG TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| AUD-001 | Create audit log | /api/audit-logs | POST | userId=1, action=VIEW, resourceType=MEDICAL_RECORD, resourceId=1 | Should return 201 Created | | ⬜ |
| AUD-002 | Get all audit logs | /api/audit-logs | GET | None | Should return list of all logs | | ⬜ |
| AUD-003 | Get specific audit log | /api/audit-logs/1 | GET | logId=1 | Should return log details | | ⬜ |
| AUD-004 | Get logs by user | /api/audit-logs/user/1 | GET | userId=1 | Should return logs for user 1 | | ⬜ |
| AUD-005 | Get logs by resource | /api/audit-logs/resource | GET | resourceType=MEDICAL_RECORD, resourceId=1 | Should return logs for resource | | ⬜ |
| AUD-006 | Verify log in database | MySQL | SELECT | SELECT * FROM access_logs | Should show created log | | ⬜ |
| AUD-007 | View logs on frontend | Dashboard | Click Audit Logs | Load logs | Should display all logs in table | | ⬜ |
| AUD-008 | Create log with CREATE action | /api/audit-logs | POST | action=CREATE | Should be recorded | | ⬜ |
| AUD-009 | Create log with UPDATE action | /api/audit-logs | POST | action=UPDATE | Should be recorded | | ⬜ |
| AUD-010 | Create log with DELETE action | /api/audit-logs | POST | action=DELETE | Should be recorded | | ⬜ |

---

## 6. CONSENT/AUTHORIZATION TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| CONSENT-001 | Create consent | /api/consents | POST | patientId=1, doctorId=1, purpose=Treatment, durationDays=180 | Should return 201 Created | | ⬜ |
| CONSENT-002 | Get consent by ID | /api/consents/1 | GET | consentId=1 | Should return consent details | | ⬜ |
| CONSENT-003 | Get consents by patient | /api/consents/patient/1 | GET | patientId=1 | Should return patient's consents | | ⬜ |
| CONSENT-004 | Check if consent is active | /api/consents/check | GET | patientId=1, doctorId=1 | Should return true/false | | ⬜ |
| CONSENT-005 | Revoke consent | /api/consents/1/revoke | PUT | consentId=1 | Should set status to REVOKED | | ⬜ |
| CONSENT-006 | Verify consent in database | MySQL | SELECT | SELECT * FROM authorizations | Should show created consent | | ⬜ |
| CONSENT-007 | Create consent with expired duration | /api/consents | POST | durationDays=1 (expired) | Should still create but mark as expired | | ⬜ |

---

## 7. FRONTEND TESTS

| Test ID | Test Case | Location | Action | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-----------------|---------------|--------|
| FE-001 | Load login page | http://localhost:8081/ | Open in browser | Should display login form | | ⬜ |
| FE-002 | Login success | Login page | Enter admin/admin123 and click Login | Should redirect to dashboard | | ⬜ |
| FE-003 | Login failure | Login page | Enter invalid credentials | Should show error message | | ⬜ |
| FE-004 | Dashboard loads | http://localhost:8081/dashboard.html | After login | Should display dashboard with statistics | | ⬜ |
| FE-005 | Navigation sidebar works | Dashboard | Click different sections | Should switch between sections | | ⬜ |
| FE-006 | Dashboard section | Dashboard | Click Dashboard nav | Should show cards with stats | | ⬜ |
| FE-007 | Appointments section | Dashboard | Click Appointments | Should load and display table | | ⬜ |
| FE-008 | Medical Records section | Dashboard | Click Medical Records | Should load and display table | | ⬜ |
| FE-009 | Users section | Dashboard | Click Users | Should load and display table | | ⬜ |
| FE-010 | Audit Logs section | Dashboard | Click Audit Logs | Should load and display table | | ⬜ |
| FE-011 | Create appointment modal | Appointments section | Click "+ New Appointment" | Should open modal form | | ⬜ |
| FE-012 | Submit appointment form | Appointment modal | Fill and submit form | Should create appointment and refresh table | | ⬜ |
| FE-013 | Delete appointment button | Appointments table | Click delete button | Should ask confirmation and delete | | ⬜ |
| FE-014 | Logout function | Dashboard | Click Logout | Should clear session and redirect to login | | ⬜ |
| FE-015 | Responsive design (mobile) | Dashboard | Resize browser to 480px | Should adapt layout for mobile | | ⬜ |
| FE-016 | Responsive design (tablet) | Dashboard | Resize browser to 768px | Should adapt layout for tablet | | ⬜ |
| FE-017 | User name display | Dashboard sidebar | After login | Should display "Welcome, admin" | | ⬜ |
| FE-018 | Table pagination (if applicable) | Any data table | Load large dataset | Should handle pagination smoothly | | ⬜ |
| FE-019 | Error notifications | Create appointment | Submit invalid data | Should show error notification | | ⬜ |
| FE-020 | Success notifications | Create appointment | Submit valid data | Should show success notification | | ⬜ |

---

## 8. DATABASE TESTS

| Test ID | Test Case | Command | Expected Result | Actual Result | Status |
|---------|-----------|---------|-----------------|---------------|--------|
| DB-001 | Check database exists | SHOW DATABASES | Should list healthcare database | | ⬜ |
| DB-002 | Check tables exist | SHOW TABLES | Should show all 8 tables | | ⬜ |
| DB-003 | Check users table | DESC users | Should have correct columns | | ⬜ |
| DB-004 | Check appointments table | DESC appointment_patients | Should have correct columns | | ⬜ |
| DB-005 | Check medical_records table | DESC medical_records | Should have correct columns | | ⬜ |
| DB-006 | Check access_logs table | DESC access_logs | Should have correct columns | | ⬜ |
| DB-007 | Verify appointment created | SELECT * FROM appointment_patients | Should contain created appointment | | ⬜ |
| DB-008 | Verify user created | SELECT * FROM users | Should contain created user | | ⬜ |
| DB-009 | Check data integrity | SELECT COUNT(*) FROM [table] | All counts should be > 0 | | ⬜ |
| DB-010 | Check timestamp fields | SELECT created_at FROM users LIMIT 1 | Should have valid timestamps | | ⬜ |

---

## 9. API SECURITY TESTS

| Test ID | Test Case | Endpoint | Method | Input | Expected Result | Actual Result | Status |
|---------|-----------|----------|--------|-------|-----------------|---------------|--------|
| SEC-001 | SQL Injection test | /api/users | GET | username=' OR '1'='1 | Should not execute SQL injection | | ⬜ |
| SEC-002 | Access without auth | /api/appointments | GET | No Authorization header | Should return 401 Unauthorized | | ⬜ |
| SEC-003 | Invalid auth header | /api/appointments | GET | Invalid Basic Auth | Should return 401 Unauthorized | | ⬜ |
| SEC-004 | CORS test | Frontend (different origin) | GET | API call from different domain | Should respect CORS policy | | ⬜ |
| SEC-005 | Role-based access | /api/users | POST | Non-admin user | Should check role authorization | | ⬜ |

---

## 10. PERFORMANCE TESTS

| Test ID | Test Case | Scenario | Expected Result | Actual Result | Status |
|---------|-----------|----------|-----------------|---------------|--------|
| PERF-001 | Load dashboard | First load after login | Should load in < 2 seconds | | ⬜ |
| PERF-002 | Load 100 appointments | /api/appointments | Should return in < 1 second | | ⬜ |
| PERF-003 | Create appointment | API call | Should complete in < 500ms | | ⬜ |
| PERF-004 | Database query performance | SELECT all records | Should handle efficiently | | ⬜ |
| PERF-005 | Concurrent logins | Multiple users | Should handle without errors | | ⬜ |

---

## Test Execution Instructions

### Prerequisites
1. ✅ Backend running: `mvn clean spring-boot:run` on port 8081
2. ✅ MySQL database running with healthcare database
3. ✅ Test data: Users (admin), Patients, Doctors in database

### How to Use This Document
1. Go through each test case row by row
2. Mark the **Actual Result** column with what happens
3. Update **Status** column with:
   - ✅ = Test passed
   - ❌ = Test failed
   - ⏳ = Test in progress
4. For failed tests, add notes about the error

### Quick Test Execution Command (PowerShell)
```powershell
# Setup auth
$pair = "admin:admin123"
$encoded = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes($pair))
$headers = @{ Authorization = "Basic $encoded"; "Content-Type" = "application/json" }

# Test appointment creation
$body = @{
    patientId = 1
    doctorId = 1
    appointmentType = "Consultation"
    appointmentDate = "2026-01-20T10:30:00"
    scheduledDuration = 30
    reason = "Checkup"
    notes = "Test"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8081/api/appointments" -Method POST -Headers $headers -Body $body
```

---

## Summary Statistics

| Category | Total Tests | Passed | Failed | Not Tested |
|----------|------------|--------|--------|-----------|
| Authentication | 6 | | | |
| Appointments | 12 | | | |
| Medical Records | 10 | | | |
| Users | 10 | | | |
| Audit Logs | 10 | | | |
| Consent | 7 | | | |
| Frontend | 20 | | | |
| Database | 10 | | | |
| Security | 5 | | | |
| Performance | 5 | | | |
| **TOTAL** | **95** | | | |

---

## Notes & Issues Found

```
(Add any issues, bugs, or observations here as you test)

Issue #1: [Description]
Status: [Open/Resolved]
Resolution: [How it was fixed]

Issue #2: [Description]
Status: [Open/Resolved]
Resolution: [How it was fixed]
```

---

**Last Updated**: January 13, 2026  
**Tester**: [Your Name]  
**Project**: Healthcare Management System
