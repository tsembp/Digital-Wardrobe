import { userService } from './user.service.js';

export const initializeProfilePage = async () => {
    if (window.location.pathname === '/profile') {
        try {
            const user = await userService.getCurrentUser();
            
            document.getElementById('header-username').innerHTML = `@${user.username}`;

            document.getElementById('username').value = user.username;
            document.getElementById('firstName').value = user.firstName;
            document.getElementById('lastName').value = user.lastName;
            document.getElementById('email').value = user.email;

            // fetch clothes
            const clothingPieces = await userService.getUserClothingPieces();
            const clothingPieceList = document.getElementById('clothingPieceList');
            clothingPieceList.innerHTML = '';
            clothingPieces.forEach(piece => {
                const card = document.createElement('div');
                card.className = 'clothing-card';

                const img = document.createElement('img');
                img.src = piece.imgUrl;
                img.alt = piece.name;
                card.appendChild(img);

                const title = document.createElement('h3');
                title.textContent = piece.name;
                card.appendChild(title);

                clothingPieceList.appendChild(card);
            });

            // fetch outfits
            const outfits = await userService.getUserOutfits();
            const outfitList = document.getElementById('outfitList');
            outfits.forEach(outfit => {
                const li = document.createElement('li');
                li.textContent = outfit.name;
                outfitList.appendChild(li);
            });

            // fetch calendar entries
            const calendarEntries = await userService.getUserCalendarEntries();
            const calendarEntryList = document.getElementById('calendarEntryList');
            calendarEntries.forEach(calendarEntry => {
                const li = document.createElement('li');
                li.textContent = calendarEntry.date;
                calendarEntryList.appendChild(li);
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

    try {
        await userService.addClothingPiece({ name, category, imgUrl });
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