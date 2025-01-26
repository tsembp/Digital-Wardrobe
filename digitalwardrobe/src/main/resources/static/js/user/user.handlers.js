import { userService } from './user.service.js';

export const initializeProfilePage = async () => {
    if (window.location.pathname === '/profile') {
        try {
            // debug for now : show brief details
            const user = await userService.getCurrentUser();
            document.getElementById('userDetails').innerHTML = `
                <p><strong>Username:</strong> ${user.username}</p>
                <p><strong>First Name:</strong> ${user.firstName}</p>
                <p><strong>Last Name:</strong> ${user.lastName}</p>
                <p><strong>Email:</strong> ${user.email}</p>
            `;
        } catch (error) {
            console.error('Profile error:', error);
            window.location.href = '/';
        }
    }
};