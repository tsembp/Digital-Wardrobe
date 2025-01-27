import { authService } from './auth.service.js';

export const initializeAuthHandlers = () => {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        // Create error message element if it doesn't exist
        let errorElement = document.querySelector('.error-message');
        if (!errorElement) {
            errorElement = document.createElement('div');
            errorElement.className = 'error-message';
            // Insert error element after h2 and before form
            loginForm.parentNode.insertBefore(errorElement, loginForm);
        }

        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            errorElement.textContent = ''; // Clear any previous error
            errorElement.style.display = 'none';
            
            try {
                const username = document.getElementById('username').value;
                const password = document.getElementById('password').value;
                
                const response = await fetch('/api/auth/authenticate/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        username: username,
                        password: password
                    })
                });

                if (!response.ok) {
                    throw new Error('Invalid username or password');
                }

                const data = await response.json();
                if (data.token) {
                    localStorage.setItem('token', data.token);
                    window.location.href = '/profile';
                }
            } catch (error) {
                console.error('Login error:', error);
                errorElement.textContent = 'Invalid username or password';
                errorElement.style.display = 'block';
            }
        });
    }
};