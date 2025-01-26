import { userService } from './user.service.js';

export const initializeProfilePage = async () => {
    if (window.location.pathname === '/profile') {
        try {
            const user = await userService.getCurrentUser();
            document.getElementById('userDetails').innerHTML = `
                <p><strong>Username:</strong> ${user.username}</p>
                <p><strong>First Name:</strong> ${user.firstName}</p>
                <p><strong>Last Name:</strong> ${user.lastName}</p>
                <p><strong>Email:</strong> ${user.email}</p>
            `;

            const outfits = await userService.getUserOutfits();
            const outfitList = document.getElementById('outfitList');
            outfits.forEach(outfit => {
                const li = document.createElement('li');
                li.textContent = outfit.name;
                outfitList.appendChild(li);
            });

            const clothingPieces = await userService.getUserClothingPieces();
            const clothingPieceList = document.getElementById('clothingPieceList');
            clothingPieces.forEach(piece => {
                const li = document.createElement('li');
                li.textContent = piece.name;
                clothingPieceList.appendChild(li);
            });
        } catch (error) {
            console.error('Profile error:', error);
            window.location.href = '/';
        }
    }
};

export const addClothingPiece = async () => {
    const name = document.getElementById('clothingPieceName').value;
    const category = document.getElementById('clothingPieceCategory').value;
    const imgUrl = document.getElementById('clothingPieceImgUrl').value;

    const clothingPiece = { name, category, imgUrl };
    console.log('Adding clothing piece:', clothingPiece); // debug

    try {
        await userService.addClothingPiece(clothingPiece);
        // await userService.addClothingPiece({ name, category, imgUrl });
        alert('Clothing piece added successfully');
        window.location.reload();
    } catch (error) {
        console.error('Add clothing piece error:', error);
        alert('Failed to add clothing piece');
    }
};

export const addOutfit = async () => {
    const name = document.getElementById('outfitName').value;
    const clothingPieceIds = document.getElementById('outfitClothingPieceIds').value.split(',').map(id => id.trim());

    try {
        await userService.addOutfit({ name, clothingPieceIds });
        alert('Outfit added successfully');
        window.location.reload();
    } catch (error) {
        console.error('Add outfit error:', error);
        alert('Failed to add outfit');
    }
};

// add functions to global scope
window.addClothingPiece = addClothingPiece;
window.addOutfit = addOutfit;