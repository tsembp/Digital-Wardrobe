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

    getUserClothingPieces: async () => {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/clothing', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error('Failed to load clothing pieces');
        }
        return response.json();
    },

    getUserCalendarEntries: async () => {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/calendarentries', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error('Failed to load calendar entries');
        }
        return response.json();
    },

    addClothingPiece: async (clothingPiece) => {
        const token = localStorage.getItem('token');
        const response = await fetch('/api/clothing', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(clothingPiece)
        });
        if (!response.ok) {
            throw new Error('Failed to add clothing piece');
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
    },

    updateClothingPiece: async (id, clothingPiece) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`/api/clothing/${id}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(clothingPiece)
        });
        if (!response.ok) {
            throw new Error('Failed to update clothing piece');
        }
        return response.json();
    },

    deleteClothingPiece: async (id) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`/api/clothing/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        });
        if (!response.ok) {
            throw new Error('Failed to delete clothing piece');
        }
    }
};