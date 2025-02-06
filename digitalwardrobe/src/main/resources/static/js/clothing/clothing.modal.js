import { initializeProfilePage } from '/js/user/user.handlers.js';
import { clothingPieceService } from '/js/clothing/clothing.service.js';

export const ClothingModal = {
    modal: null,
    form: null,
    imagePreview: null,
    editModal: null,
    editForm: null,
    editImagePreview: null,
    currentClothingPieceId: null,

    init() {
        this.createModal();
        this.createEditModal();
        this.initializeEventListeners();
        this.fetchCategories();
    },

    // Modal for creating new clothing piece
    createModal() {
        this.modal = document.createElement('div');
        this.modal.className = 'modal';
        this.modal.id = 'addClothingModal';
        
        // modal content fields
        const modalContent = `
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Add New Clothing Item</h2>
                <form id="addClothingForm" class="clothing-form">
                    <div class="form-group">
                        <label for="clothingName">Name:</label>
                        <input type="text" id="clothingName" name="name" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="clothingCategory">Category:</label>
                        <select id="clothingCategory" name="category" required>
                            <option value="">Select a category</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="clothingColor">Color:</label>
                        <div id="colorSelection" class="color-selection">
                            <div class="color-circle" data-color="red" style="background-color: red;"></div>
                            <div class="color-circle" data-color="blue" style="background-color: blue;"></div>
                            <div class="color-circle" data-color="green" style="background-color: green;"></div>
                            <div class="color-circle" data-color="yellow" style="background-color: yellow;"></div>
                            <div class="color-circle" data-color="black" style="background-color: black;"></div>
                            <div class="color-circle" data-color="white" style="background-color: white;"></div>
                            <div class="color-circle" data-color="gray" style="background-color: gray;"></div>
                            <div class="color-circle" data-color="purple" style="background-color: #A020F0;"></div>
                            <div class="color-circle" data-color="orange" style="background-color: orange;"></div>
                            <div class="color-circle" data-color="pink" style="background-color: pink;"></div>
                            <div class="color-circle" data-color="brown" style="background-color: #964B00;"></div>
                        </div>
                        <input type="hidden" id="clothingColor" name="color" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="clothingImage">Image:</label>
                        <input type="file" id="clothingImage" name="image" accept="image/*" required>
                        <div id="imagePreview" class="image-preview"></div>
                    </div>

                    <div class="form-buttons">
                        <button type="submit" class="submit-btn">Add Item</button>
                    </div>
                </form>
            </div>
        `;
        
        this.modal.innerHTML = modalContent;
        document.body.appendChild(this.modal);

        // references for frequently used elements
        this.form = this.modal.querySelector('#addClothingForm');
        this.imagePreview = this.modal.querySelector('#imagePreview');

        // Add event listeners for color selection
        const colorCircles = this.modal.querySelectorAll('.color-circle');
        colorCircles.forEach(circle => {
            circle.addEventListener('click', (e) => {
                colorCircles.forEach(c => c.classList.remove('selected'));
                e.target.classList.add('selected');
                this.modal.querySelector('#clothingColor').value = e.target.getAttribute('data-color');
            });
        });
    },

    // Modal for editing existing clothing piece
    createEditModal() {
        this.editModal = document.getElementById('editClothingModal');
        this.editForm = this.editModal.querySelector('#editClothingForm');
        this.editImagePreview = this.editModal.querySelector('#editImagePreview');
    },

    async fetchCategories() {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch('/api/categories', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (!response.ok) {x    
                throw new Error('Failed to fetch categories');
            }
            const categories = await response.json();
            this.populateCategories(categories);
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    },

    populateCategories(categories) {
        const addCategorySelect = this.modal.querySelector('#clothingCategory');
        const editCategorySelect = this.editForm.querySelector('#editClothingCategory');
    
        categories.forEach(category => {
            const addOption = document.createElement('option');
            addOption.value = category;
            addOption.textContent = category.charAt(0) + category.slice(1).toLowerCase();
            addCategorySelect.appendChild(addOption);
    
            const editOption = document.createElement('option');
            editOption.value = category;
            editOption.textContent = category.charAt(0) + category.slice(1).toLowerCase();
            editCategorySelect.appendChild(editOption);
        });
    },

    initializeEventListeners() {
        // Add modal
        const addButton = document.getElementById('addClothingBtn');
        addButton.addEventListener('click', () => this.openModal());

        const closeBtn = this.modal.querySelector('.close');
        closeBtn.addEventListener('click', () => this.closeModal());

        window.addEventListener('click', (e) => {
            if (e.target === this.modal) {
                this.closeModal();
            }
        });

        const imageInput = this.modal.querySelector('#clothingImage');
        imageInput.addEventListener('change', (e) => this.handleImagePreview(e));

        this.form.addEventListener('submit', (e) => this.handleSubmit(e));

        // Edit modal
        const editCloseBtn = this.editModal.querySelector('.close');
        editCloseBtn.addEventListener('click', () => this.closeEditModal());

        this.editForm.addEventListener('submit', (e) => this.handleEditSubmit(e));
    },

    // Open & close for create modal
    openModal() {
        this.modal.style.display = 'block';
    },

    closeModal() {
        this.modal.style.display = 'none';
        this.form.reset();
        this.imagePreview.innerHTML = '';
    },

    // Open & close for edit modal
    openEditModal(clothingPiece) {
        this.currentClothingPieceId = clothingPiece.id;
        this.editForm.querySelector('#editClothingName').value = clothingPiece.name;
        this.editForm.querySelector('#editClothingCategory').value = clothingPiece.category;
        this.editImagePreview.innerHTML = `<img src="${clothingPiece.imgUrl}" alt="Preview">`;
        this.editModal.style.display = 'block';
    },

    closeEditModal() {
        this.editModal.style.display = 'none';
        this.editForm.reset();
        this.editImagePreview.innerHTML = '';
    },

    handleImagePreview(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                this.imagePreview.innerHTML = `<img src="${e.target.result}" alt="Preview">`;
            };
            reader.readAsDataURL(file);
        }
    },

    // Create new submit logic
    async handleSubmit(e) {
        e.preventDefault();
        
        try {
            const formData = new FormData(this.form);
            const token = localStorage.getItem('token');

            // new clothing piece
            const clothingPiece = {
                name: formData.get('name'),
                category: formData.get('category'),
                color: formData.get('color').toUpperCase(),
                imgUrl: ""
            };

            console.log('Adding clothing piece:');
            console.table(clothingPiece);

            const createdClothingPiece = await clothingPieceService.addClothingPiece(clothingPiece);
            const clothingPieceId = createdClothingPiece.id;
            
            // upload image to firebase
            const imageFile = formData.get('image');
            const imageFormData = new FormData();
            imageFormData.append('file', imageFile);
            imageFormData.append('text', clothingPieceId);

            const imageUploadResponse = await fetch('/api/files/upload', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: imageFormData
            });

            if (!imageUploadResponse.ok) {
                throw new Error('Failed to upload image');
            }

            // get uploaded imgUrl and assign it to new clothing piece
            const imgResponseText = await imageUploadResponse.text();
            const imgUrl = imgResponseText.includes('Image URL: ')
                ? imgResponseText.split('Image URL: ')[1].split('\n')[0].trim()
                : null;
            clothingPiece.imgUrl = imgUrl;
            await clothingPieceService.updateClothingPiece(clothingPieceId, clothingPiece);

            // Refresh the clothing list
            await initializeProfilePage();
            
            // Close the modal and reset form
            this.closeModal();

            // Show success message
            alert('Clothing item added successfully!');
        } catch (error) {
            console.error('Error adding clothing item:', error);
            alert('Failed to add clothing item. Please try again.');
        }
    },

    // Edit submit logic
    async handleEditSubmit(e) {
        e.preventDefault();
        
        try {
            const formData = new FormData(this.editForm);
            const token = localStorage.getItem('token');

            const clothingPiece = {
                name: formData.get('name'),
                category: formData.get('category'),
                imgUrl: this.editImagePreview.querySelector('img').src
            };

            await clothingPieceService.updateClothingPiece(this.currentClothingPieceId, clothingPiece);

            // Refresh the clothing list
            await initializeProfilePage();
            
            // Close the modal and reset form
            this.closeEditModal();

            // Show success message
            alert('Clothing item updated successfully!');
        } catch (error) {
            console.error('Error updating clothing item:', error);
            alert('Failed to update clothing item. Please try again.');
        }
    },

    async handleDelete() {
        try {
            await clothingPieceService.deleteClothingPiece(this.currentClothingPieceId);

            // Refresh the clothing list
            await initializeProfilePage();
            
            // Close the modal
            this.closeEditModal();

            // Show success message
            alert('Clothing item deleted successfully!');
        } catch (error) {
            console.error('Error deleting clothing item:', error);
            alert('Failed to delete clothing item. Please try again.');
        }
    }
};

// Initialize when document is loaded
document.addEventListener('DOMContentLoaded', () => ClothingModal.init());