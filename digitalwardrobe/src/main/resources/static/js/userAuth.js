// Only run loadUserProfile if we're on the profile page
if (window.location.pathname === '/profile') {
    loadUserProfile();
}

// Login form handler - only attach if we're on the login page
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        fetch('/api/auth/authenticate/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem('token', data.token);
                window.location.href = '/profile';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Login failed. Please check your credentials.');
        });
    });
}

// Fetch user's data
function loadUserProfile() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/';
        return;
    }

    fetch('/api/users/user', {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to load profile');
        }
        return response.json();
    })
    .then(user => {
        document.getElementById('userDetails').innerHTML = `
            <p><strong>Username:</strong> ${user.username}</p>
            <p><strong>First Name:</strong> ${user.firstName}</p>
            <p><strong>Last Name:</strong> ${user.lastName}</p>
            <p><strong>Email:</strong> ${user.email}</p>
        `;
    })
    .catch(error => {
        console.error('Error:', error);
        window.location.href = '/';
    });
}

function logout() {
    const token = localStorage.getItem('token');
    fetch('/api/auth/logout', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .then(() => {
        localStorage.removeItem('token');
        window.location.href = '/';
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

if (window.location.pathname === '/profile') {
    loadUserProfile();
}