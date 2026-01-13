// ==================== API FUNCTIONS ====================

const API_BASE_URL = 'http://localhost:8081/api';

// Get Auth Header
function getAuthHeader() {
    const credentials = sessionStorage.getItem('authCredentials');
    if (!credentials) {
        window.location.href = 'index.html';
        return null;
    }
    return {
        'Authorization': `Basic ${credentials}`,
        'Content-Type': 'application/json'
    };
}

// Check if user is authenticated
function checkAuth() {
    if (!sessionStorage.getItem('authCredentials')) {
        window.location.href = 'index.html';
    }
}

// ==================== APPOINTMENTS API ====================

async function fetchAppointments() {
    try {
        const response = await fetch(`${API_BASE_URL}/appointments`, {
            method: 'GET',
            headers: getAuthHeader()
        });
        
        if (response.ok) {
            return await response.json();
        } else if (response.status === 403) {
            // If no appointments found or access denied, return empty array
            return [];
        }
        return [];
    } catch (error) {
        console.error('Error fetching appointments:', error);
        return [];
    }
}

async function createAppointment(data) {
    try {
        const response = await fetch(`${API_BASE_URL}/appointments`, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            const error = await response.json();
            throw new Error(error.error || 'Failed to create appointment');
        }
    } catch (error) {
        console.error('Error creating appointment:', error);
        throw error;
    }
}

async function getAppointmentsByPatient(patientId) {
    try {
        const response = await fetch(`${API_BASE_URL}/appointments/patient/${patientId}`, {
            method: 'GET',
            headers: getAuthHeader()
        });
        
        if (response.ok) {
            return await response.json();
        }
        return [];
    } catch (error) {
        console.error('Error fetching patient appointments:', error);
        return [];
    }
}

async function deleteAppointment(appointmentId) {
    try {
        const response = await fetch(`${API_BASE_URL}/appointments/${appointmentId}`, {
            method: 'DELETE',
            headers: getAuthHeader()
        });
        
        return response.ok;
    } catch (error) {
        console.error('Error deleting appointment:', error);
        return false;
    }
}

// ==================== MEDICAL RECORDS API ====================

async function fetchMedicalRecords() {
    try {
        const response = await fetch(`${API_BASE_URL}/medical-records`, {
            method: 'GET',
            headers: getAuthHeader()
        });
        
        if (response.ok) {
            return await response.json();
        }
        return [];
    } catch (error) {
        console.error('Error fetching medical records:', error);
        return [];
    }
}

async function createMedicalRecord(data) {
    try {
        const url = `${API_BASE_URL}/medical-records?patientId=${data.patientId}&doctorId=${data.doctorId}`;
        const response = await fetch(url, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            const error = await response.json();
            throw new Error(error.error || 'Failed to create medical record');
        }
    } catch (error) {
        console.error('Error creating medical record:', error);
        throw error;
    }
}

// ==================== USERS API ====================

async function fetchUsers() {
    try {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: 'GET',
            headers: getAuthHeader()
        });
        
        if (response.ok) {
            return await response.json();
        }
        return [];
    } catch (error) {
        console.error('Error fetching users:', error);
        return [];
    }
}

async function createUser(data) {
    try {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            return await response.json();
        } else {
            const error = await response.json();
            throw new Error(error.error || 'Failed to create user');
        }
    } catch (error) {
        console.error('Error creating user:', error);
        throw error;
    }
}

// ==================== AUDIT LOGS API ====================

async function fetchAuditLogs() {
    try {
        const response = await fetch(`${API_BASE_URL}/audit-logs`, {
            method: 'GET',
            headers: getAuthHeader()
        });
        
        if (response.ok) {
            return await response.json();
        }
        return [];
    } catch (error) {
        console.error('Error fetching audit logs:', error);
        return [];
    }
}

// ==================== GENERAL FUNCTIONS ====================

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
}

function showNotification(message, type = 'success') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        background-color: ${type === 'success' ? '#27ae60' : '#e74c3c'};
        color: white;
        border-radius: 5px;
        z-index: 10000;
        animation: slideIn 0.3s ease-in;
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.remove();
    }, 3000);
}
