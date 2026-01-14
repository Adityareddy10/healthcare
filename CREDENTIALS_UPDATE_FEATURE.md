# User Credentials Update Feature

## Overview
Users, patients, and doctors can now update their own username and password after registration. All passwords are securely encoded using BCrypt.

## New API Endpoints

### 1. Update Password
**Endpoint:** `POST /api/auth/update-password`  
**Authentication:** Required (Basic Auth)  
**Description:** Allows authenticated users to change their password

**Request Body:**
```json
{
  "oldPassword": "currentPassword123",
  "newPassword": "newPassword456",
  "confirmPassword": "newPassword456"
}
```

**Response (Success):**
```json
{
  "message": "Password updated successfully",
  "userId": 1,
  "username": "john_doe"
}
```

**Response (Error):**
- Old password incorrect: `401 Unauthorized`
- Passwords don't match: `400 Bad Request`
- New password too short (< 6 chars): `400 Bad Request`

---

### 2. Update Username
**Endpoint:** `POST /api/auth/update-username`  
**Authentication:** Required (Basic Auth)  
**Description:** Allows authenticated users to change their username

**Request Body:**
```json
{
  "newUsername": "john_smith",
  "password": "currentPassword123"
}
```

**Response (Success):**
```json
{
  "message": "Username updated successfully",
  "userId": 1,
  "oldUsername": "john_doe",
  "newUsername": "john_smith"
}
```

**Response (Error):**
- Username already exists: `400 Bad Request`
- Invalid password: `401 Unauthorized`

---

### 3. Update Profile
**Endpoint:** `POST /api/auth/update-profile`  
**Authentication:** Required (Basic Auth)  
**Description:** Allows patients and doctors to update their profile information

**For Patients - Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1-555-1234",
  "address": "123 Main St",
  "city": "Springfield",
  "state": "IL",
  "zipCode": "62701"
}
```

**For Doctors - Request Body:**
```json
{
  "firstName": "Dr. Jane",
  "lastName": "Smith",
  "phoneNumber": "+1-555-5678",
  "specialization": "Cardiology",
  "department": "Heart & Vascular"
}
```

**Response (Success - Patient):**
```json
{
  "message": "Patient profile updated successfully",
  "userId": 1,
  "patientId": 5,
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1-555-1234"
}
```

**Response (Success - Doctor):**
```json
{
  "message": "Doctor profile updated successfully",
  "userId": 2,
  "doctorId": 3,
  "firstName": "Dr. Jane",
  "lastName": "Smith",
  "specialization": "Cardiology"
}
```

---

## Existing Registration Endpoints (Updated with Password Encoding)

### Register as Patient
**Endpoint:** `POST /api/auth/register/patient`  
**Description:** Register a new patient account with credentials

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "securePassword123",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "gender": "Male",
  "medicalRecordNumber": "MRN-001",
  "phoneNumber": "+1-555-1234",
  "address": "123 Main St",
  "city": "Springfield",
  "state": "IL",
  "zipCode": "62701",
  "bloodType": "O+"
}
```

### Register as Doctor
**Endpoint:** `POST /api/auth/register/doctor`  
**Description:** Register a new doctor account with credentials

**Request Body:**
```json
{
  "username": "dr_jane_smith",
  "password": "securePassword456",
  "email": "jane@hospital.com",
  "firstName": "Jane",
  "lastName": "Smith",
  "licenseNumber": "MD123456",
  "specialization": "Cardiology",
  "department": "Heart & Vascular",
  "phoneNumber": "+1-555-5678"
}
```

### Register as Admin
**Endpoint:** `POST /api/auth/register/admin`  
**Description:** Register a new admin account

**Request Body:**
```json
{
  "username": "admin_user",
  "password": "adminPassword789",
  "email": "admin@hospital.com"
}
```

---

## Security Features

✅ **Password Encoding:** All passwords are hashed using BCrypt  
✅ **Authentication Required:** All update endpoints require valid credentials  
✅ **Password Verification:** Old password must be verified before updating  
✅ **Duplicate Prevention:** Usernames and emails must be unique  
✅ **Validation:** Minimum password length of 6 characters  
✅ **Role-Based Access:** Profile updates support patient and doctor roles  

---

## Usage Examples

### Example 1: User Changes Password
```bash
curl -X POST http://localhost:8081/api/auth/update-password \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic am9obl9kb2U6c2VjdXJlUGFzc3dvcmQxMjM=" \
  -d '{
    "oldPassword": "securePassword123",
    "newPassword": "newSecurePassword456",
    "confirmPassword": "newSecurePassword456"
  }'
```

### Example 2: User Changes Username
```bash
curl -X POST http://localhost:8081/api/auth/update-username \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic am9obl9kb2U6c2VjdXJlUGFzc3dvcmQxMjM=" \
  -d '{
    "newUsername": "john_smith",
    "password": "securePassword123"
  }'
```

### Example 3: Patient Updates Profile
```bash
curl -X POST http://localhost:8081/api/auth/update-profile \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic am9obl9kb2U6c2VjdXJlUGFzc3dvcmQxMjM=" \
  -d '{
    "firstName": "John",
    "lastName": "Smith",
    "phoneNumber": "+1-555-9999",
    "city": "New Springfield"
  }'
```

---

## Implementation Details

**Files Modified:**
- `AuthController.java` - Added three new endpoints for credential and profile updates
- Password encoding now applied to all registration endpoints using BCryptPasswordEncoder

**Key Methods:**
- `updatePassword()` - Validates old password before updating to new one
- `updateUsername()` - Checks username availability before updating
- `updateProfile()` - Updates patient or doctor profile based on user role

**Error Handling:**
- `400 Bad Request` - Invalid input or validation failure
- `401 Unauthorized` - Authentication failed or invalid credentials
- `404 Not Found` - User or profile not found
- `500 Internal Server Error` - Unexpected server error

---

## Next Steps

1. Test all endpoints using curl or Postman
2. Add frontend UI components for password and username changes
3. Add frontend profile editing forms for patients and doctors
4. Consider adding email verification for sensitive changes
5. Add audit logging for credential changes
