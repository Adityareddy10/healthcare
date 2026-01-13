// ==================== DASHBOARD FUNCTIONS ====================

// Check authentication on page load
document.addEventListener('DOMContentLoaded', function() {
    checkAuth();
    
    // Set user display
    const username = sessionStorage.getItem('username');
    const userDisplay = document.getElementById('userDisplay');
    if (userDisplay) {
        userDisplay.textContent = `Welcome, ${username}`;
    }
    
    // Load initial data
    loadDashboardData();
    loadAppointments();
    loadMedicalRecords();
    loadUsers();
    loadAuditLogs();
    
    // Setup form handlers
    const appointmentForm = document.getElementById('appointmentForm');
    if (appointmentForm) {
        appointmentForm.addEventListener('submit', handleCreateAppointment);
    }
});

// ==================== SECTION NAVIGATION ====================

function showSection(sectionId) {
    // Hide all sections
    const sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.classList.remove('active');
    });
    
    // Remove active class from nav links
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.classList.remove('active');
    });
    
    // Show selected section
    const selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.classList.add('active');
    }
    
    // Add active class to clicked nav link
    event.target.classList.add('active');
    
    // Reload data for the section
    if (sectionId === 'appointments') {
        loadAppointments();
    } else if (sectionId === 'medical-records') {
        loadMedicalRecords();
    } else if (sectionId === 'users') {
        loadUsers();
    } else if (sectionId === 'audit-logs') {
        loadAuditLogs();
    }
}

// ==================== DASHBOARD DATA ====================

async function loadDashboardData() {
    try {
        const appointments = await fetchAppointments();
        const users = await fetchUsers();
        
        document.getElementById('totalAppointments').textContent = appointments.length || 0;
        document.getElementById('totalPatients').textContent = '5'; // Demo value
        document.getElementById('totalDoctors').textContent = '3'; // Demo value
        document.getElementById('totalRecords').textContent = '8'; // Demo value
    } catch (error) {
        console.error('Error loading dashboard data:', error);
    }
}

// ==================== APPOINTMENTS ====================

async function loadAppointments() {
    try {
        const appointments = await fetchAppointments();
        const tbody = document.getElementById('appointmentsTable');
        
        if (appointments.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" class="center">No appointments found</td></tr>';
            return;
        }
        
        tbody.innerHTML = appointments.map(apt => `
            <tr>
                <td>${apt.appointmentId}</td>
                <td>${apt.patientId}</td>
                <td>${apt.doctorId}</td>
                <td>${apt.appointmentType}</td>
                <td>${formatDate(apt.appointmentDate)}</td>
                <td><span class="status-badge ${apt.status.toLowerCase()}">${apt.status}</span></td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-view" onclick="viewAppointment(${apt.appointmentId})">View</button>
                        <button class="btn-delete" onclick="deleteAppointmentHandler(${apt.appointmentId})">Delete</button>
                    </div>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading appointments:', error);
    }
}

function openAppointmentModal() {
    const modal = document.getElementById('appointmentModal');
    if (modal) {
        modal.classList.add('show');
    }
}

function closeAppointmentModal() {
    const modal = document.getElementById('appointmentModal');
    if (modal) {
        modal.classList.remove('show');
    }
}

async function handleCreateAppointment(e) {
    e.preventDefault();
    
    const data = {
        patientId: parseInt(document.getElementById('patientId').value),
        doctorId: parseInt(document.getElementById('doctorId').value),
        appointmentType: document.getElementById('appointmentType').value,
        appointmentDate: new Date(document.getElementById('appointmentDate').value).toISOString().slice(0, 19),
        scheduledDuration: parseInt(document.getElementById('scheduledDuration').value) || 30,
        reason: document.getElementById('reason').value,
        notes: document.getElementById('notes').value
    };
    
    try {
        await createAppointment(data);
        showNotification('Appointment created successfully!', 'success');
        closeAppointmentModal();
        document.getElementById('appointmentForm').reset();
        loadAppointments();
    } catch (error) {
        showNotification('Error creating appointment: ' + error.message, 'error');
    }
}

async function deleteAppointmentHandler(appointmentId) {
    if (confirm('Are you sure you want to delete this appointment?')) {
        const success = await deleteAppointment(appointmentId);
        if (success) {
            showNotification('Appointment deleted successfully!', 'success');
            loadAppointments();
        } else {
            showNotification('Error deleting appointment', 'error');
        }
    }
}

function viewAppointment(appointmentId) {
    alert('View details for appointment ' + appointmentId + '\n(Feature to be implemented)');
}

// ==================== MEDICAL RECORDS ====================

async function loadMedicalRecords() {
    try {
        const records = await fetchMedicalRecords();
        const tbody = document.getElementById('recordsTable');
        
        if (records.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" class="center">No medical records found</td></tr>';
            return;
        }
        
        tbody.innerHTML = records.map(record => `
            <tr>
                <td>${record.recordId}</td>
                <td>${record.patientId}</td>
                <td>${record.doctorId}</td>
                <td>${record.diagnosis || 'N/A'}</td>
                <td>${record.recordType}</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-view" onclick="viewRecord(${record.recordId})">View</button>
                        <button class="btn-delete" onclick="deleteRecord(${record.recordId})">Delete</button>
                    </div>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading medical records:', error);
    }
}

function openRecordModal() {
    // TODO: Implement record modal
    alert('Create Medical Record\n(Feature to be implemented)');
}

function viewRecord(recordId) {
    alert('View details for record ' + recordId + '\n(Feature to be implemented)');
}

async function deleteRecord(recordId) {
    if (confirm('Are you sure you want to delete this record?')) {
        // TODO: Implement delete
        alert('Delete record ' + recordId + '\n(Feature to be implemented)');
    }
}

// ==================== USERS ====================

async function loadUsers() {
    try {
        const users = await fetchUsers();
        const tbody = document.getElementById('usersTable');
        
        if (users.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="center">No users found</td></tr>';
            return;
        }
        
        tbody.innerHTML = users.map(user => `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email || 'N/A'}</td>
                <td><span class="role-badge">${user.role}</span></td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-edit" onclick="editUser(${user.id})">Edit</button>
                        <button class="btn-delete" onclick="deleteUser(${user.id})">Delete</button>
                    </div>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

function openUserModal() {
    alert('Create New User\n(Feature to be implemented)');
}

function editUser(userId) {
    alert('Edit user ' + userId + '\n(Feature to be implemented)');
}

async function deleteUser(userId) {
    if (confirm('Are you sure you want to delete this user?')) {
        alert('Delete user ' + userId + '\n(Feature to be implemented)');
    }
}

// ==================== AUDIT LOGS ====================

async function loadAuditLogs() {
    try {
        const logs = await fetchAuditLogs();
        const tbody = document.getElementById('logsTable');
        
        if (logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" class="center">No audit logs found</td></tr>';
            return;
        }
        
        tbody.innerHTML = logs.map(log => `
            <tr>
                <td>${log.id}</td>
                <td>${log.userId}</td>
                <td>${log.action}</td>
                <td>${log.resourceType}</td>
                <td>${log.resourceId}</td>
                <td>${formatDate(log.timestamp)}</td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading audit logs:', error);
    }
}
