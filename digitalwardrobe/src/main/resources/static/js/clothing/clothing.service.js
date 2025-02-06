// supports CRUD operations for clothing pieces
export const clothingPieceService = {
    // create
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

    // read / get
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

    // update
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

    // delete
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