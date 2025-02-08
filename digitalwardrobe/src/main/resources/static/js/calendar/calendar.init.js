import { calendarEntryService } from './calendar.service.js';
import { userService } from '../user/user.service.js';

import { Calendar } from 'https://cdn.skypack.dev/@fullcalendar/core';
import dayGridPlugin from 'https://cdn.skypack.dev/@fullcalendar/daygrid';

document.addEventListener('DOMContentLoaded', () => {
    initializeCalendar();
});

export function initializeCalendar() {
    const calendarEl = document.getElementById('full-calendar');
    const calendar = new Calendar(calendarEl, {
        plugins: [dayGridPlugin],
        initialView: 'dayGridMonth',
        events: async function(fetchInfo, successCallback, failureCallback) {
            try {
                const entries = await calendarEntryService.getUserCalendarEntries();
                const events = entries.map(entry => ({
                    title: entry.outfitName,
                    start: entry.date,
                    extendedProps: {
                        outfitId: entry.outfitId,
                        occasion: entry.occasion,
                        notes: entry.notes
                    }   
                }));
                successCallback(events);
            } catch (error) {
                failureCallback(error);
            }
        },
        eventClick: function(info) {
            openViewOutfitModal({
                title: info.event.title,
                start: info.event.startStr,
                ...info.event.extendedProps
            });
        }
    });
    calendar.render();
}

function openViewOutfitModal(entry) {
    console.log('Viewing outfit with id=' + entry.outfitId);

    const modal = document.getElementById('viewOutfitModal');
    const outfitDetails = document.getElementById('outfitDetails');
    outfitDetails.innerHTML = 'Loading...';

    // Fetch outfit details from the backend
    userService.getOutfit(entry.outfitId).then(outfit => {
        let detailsHtml = `
            <p><strong>Outfit:</strong> ${outfit.name}</p>
            <p><strong>Date:</strong> ${entry.start}</p>
            <p><strong>Occasion:</strong> ${entry.occasion || 'N/A'}</p>
            <p><strong>Notes:</strong> ${entry.notes || 'No notes provided'}</p>
            <p><strong>Clothing Pieces:</strong> ${outfit.clothingPieceIds}</p>
        `;

        // If more details are added later, you can loop through entry keys dynamically
        Object.keys(entry).forEach(key => {
            if (!['title', 'start', 'outfitId', 'occasion', 'notes'].includes(key)) {
                detailsHtml += `<p><strong>${key}:</strong> ${entry[key]}</p>`;
            }
        });

        outfitDetails.innerHTML = detailsHtml;
    }).catch(error => {
        outfitDetails.innerHTML = 'Failed to load outfit details.';
        console.error('Error fetching outfit details:', error);
    });

    modal.style.display = 'block';

    // close modal from button
    const closeBtn = modal.querySelector('.close');
    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };
}


// Close modal on click outside
    window.onclick = function(event) {
    const modal = document.getElementById('viewOutfitModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
};