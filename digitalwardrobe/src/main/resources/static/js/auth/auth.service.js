export const authService = {
    // Login
    login: async (username, password) => {
        const response = await fetch('/api/auth/authenticate/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });
        if (!response.ok) {
            throw new Error('Login failed');
        }
        return response.json();
    },

    // Logout
    logout: async () => {
        const token = localStorage.getItem('token');
        await fetch('/api/auth/logout', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        localStorage.removeItem('token');
    },

    //Register
    register: async (userData) => {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        if (!response.ok) {
            throw new Error('Registration failed');
        }
        return response.json();
    }
};