export const calendarEntryService = {
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
    }
}