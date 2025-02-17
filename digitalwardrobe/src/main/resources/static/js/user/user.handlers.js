import { ClothingModal } from '/js/clothing/clothing.modal.js';
import { clothingPieceService } from '/js/clothing/clothing.service.js';
import { userService } from './user.service.js';
import { initializeCalendar } from '/js/calendar/calendar.init.js';

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
            const clothingPieces = await clothingPieceService.getUserClothingPieces();
            const clothingPieceList = document.getElementById('clothingPieceList');
            clothingPieceList.innerHTML = '';
            clothingPieces.forEach(piece => {
                // card
                const card = document.createElement('div');
                card.className = 'clothing-card';
                card.setAttribute('data-category', piece.category);
                card.setAttribute('data-color', piece.color);

                // image preview
                const img = document.createElement('img');
                img.src = piece.imgUrl;
                img.alt = piece.name;
                card.appendChild(img);

                // clothing piece name
                const title = document.createElement('h3');
                title.textContent = piece.name;
                card.appendChild(title);

                // edit & delete buttons on hover
                const buttonsDiv = document.createElement('div');
                buttonsDiv.className = 'card-buttons';

                const editButton = document.createElement('button');
                editButton.className = 'edit-btn';
                editButton.innerHTML = '<i class="fas fa-edit"></i>';
                editButton.addEventListener('click', (e) => {
                    e.stopPropagation();
                    ClothingModal.openEditModal(piece);
                });

                // Create Delete button
                const deleteButton = document.createElement('button');
                deleteButton.className = 'delete-btn';
                deleteButton.innerHTML = '<i class="fas fa-trash"></i>';
                deleteButton.addEventListener('click', (e) => {
                    e.stopPropagation();
                    clothingPieceService.deleteClothingPiece(piece.id)
                        .then(() => initializeProfilePage()) // refresh clothing piece list
                        .catch(error => console.error('Delete clothing piece error:', error));
                });

                buttonsDiv.appendChild(editButton);
                buttonsDiv.appendChild(deleteButton);
                card.appendChild(buttonsDiv);

                // card.addEventListener('click', () => ClothingModal.openEditModal(piece));

                clothingPieceList.appendChild(card);
            });

            // set number of clothing pieces
            document.getElementById('clothingCount').innerHTML = clothingPieces.length;

            // fetch outfits
            const outfits = await userService.getUserOutfits();
            const outfitList = document.getElementById('outfitList');
            outfitList.innerHTML = ''; // Clear existing list
            outfits.forEach(outfit => {
                const li = document.createElement('li');
                li.textContent = outfit.name;
                outfitList.appendChild(li);
            });

            // set number of outfits created
            document.getElementById('outfitCount').innerHTML = outfits.length;

            // Calculate most popular color
            const colorCounts = clothingPieces.reduce((acc, piece) => {
                acc[piece.color] = (acc[piece.color] || 0) + 1;
                return acc;
            }, {});

            const mostPopularColor = Object.entries(colorCounts).reduce((a, b) => 
                (b[1] > a[1] ? b : a), ['none', 0]);
            
            document.getElementById('popularColor').innerHTML = 
                mostPopularColor[0] === 'none' ? 'None' : 
                `${mostPopularColor[0].charAt(0).toUpperCase() + mostPopularColor[0].slice(1).toLowerCase()} (${mostPopularColor[1]})`;

            // initialize calendar 
            initializeCalendar();
        } catch (error) {
            console.error('Profile error:', error);
            window.location.href = '/';
        }
    }
};