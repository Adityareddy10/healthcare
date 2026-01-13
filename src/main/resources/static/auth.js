// ==================== AUTHENTICATION ==================== 

const API_BASE_URL = 'http://localhost:8081/api';

// Store credentials in sessionStorage
function storeAuth(username, password) {
    const credentials = btoa(`${username}:${password}`);
    sessionStorage.setItem('authCredentials', credentials);
    sessionStorage.setItem('username', username);
}

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

function logout() {
    sessionStorage.clear();
    window.location.href = 'index.html';
}

// Login Form Handler
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const errorDiv = document.getElementById('loginError');
            
            try {
                // Test login with a simple API call
                const credentials = btoa(`${username}:${password}`);
                const response = await fetch(`${API_BASE_URL}/auth/check-username/${username}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Basic ${credentials}`
                    }
                });
                
                if (response.ok) {
                    // Login successful
                    storeAuth(username, password);
                    window.location.href = 'dashboard.html';
                } else {
                    errorDiv.textContent = 'Invalid username or password';
                    errorDiv.classList.add('show');
                }
            } catch (error) {
                errorDiv.textContent = 'Connection error. Please try again.';
                errorDiv.classList.add('show');
                console.error('Login error:', error);
            }
        });
    }
});
