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
    },

    async getOutfit(outfitId) {
        const token = localStorage.getItem('token');
        const response = await fetch(`/api/outfit/${outfitId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error('Failed to load outfits');
        }

        return response.json();
    },

    getUserOutfits: async () => {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/outfit', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error('Failed to load outfits');
        }

        return response.json();
    },

    addOutfit: async (outfit) => {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/outfit', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(outfit)
        });
        if (!response.ok) {
            throw new Error('Failed to add outfit');
        }
        return response.json();
    }
};