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

            // fetch outfits
            const outfits = await userService.getUserOutfits();
            const outfitList = document.getElementById('outfitList');
            outfits.forEach(outfit => {
                const li = document.createElement('li');
                li.textContent = outfit.name;
                outfitList.appendChild(li);
            });

            // initialize calendar 
            initializeCalendar();
            // const calendarEntries = await calendarEntryService.getUserCalendarEntries();
            // const calendarEntryList = document.getElementById('calendarEntryList');
            // calendarEntries.forEach(calendarEntry => {
            //     const li = document.createElement('li');
            //     li.textContent = calendarEntry.date;
            //     calendarEntryList.appendChild(li);
            // });
        } catch (error) {
            console.error('Profile error:', error);
            // window.location.href = '/';
        }
    }
};