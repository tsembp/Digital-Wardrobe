export const userService = {
    getCurrentUser: async () => {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/users/user', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error('Failed to load profile');
        }
        return response.json();
    }
};