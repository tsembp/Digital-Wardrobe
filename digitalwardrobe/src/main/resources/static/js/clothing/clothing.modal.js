import { initializeProfilePage } from '/js/user/user.handlers.js';
import { userService } from '/js/user/user.service.js';

export const ClothingModal = {
    modal: null,
    form: null,
    imagePreview: null,

    init() {
        this.createModal();
        this.initializeEventListeners();
        this.fetchCategories();
    },

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
        const categorySelect = this.modal.querySelector('#clothingCategory');
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category;
            option.textContent = category.charAt(0) + category.slice(1).toLowerCase();
            categorySelect.appendChild(option);
        });
    },

    initializeEventListeners() {
        // Add button on click handler
        const addButton = document.getElementById('addClothingBtn');
        addButton.addEventListener('click', () => this.openModal());

        // Close button click handler
        const closeBtn = this.modal.querySelector('.close');
        closeBtn.addEventListener('click', () => this.closeModal());

        // Click outside modal => close
        window.addEventListener('click', (e) => {
            if (e.target === this.modal) {
                this.closeModal();
            }
        });

        // Image preview handler
        const imageInput = this.modal.querySelector('#clothingImage');
        imageInput.addEventListener('change', (e) => this.handleImagePreview(e));

        // Form submission handler
        this.form.addEventListener('submit', (e) => this.handleSubmit(e));
    },

    openModal() {
        this.modal.style.display = 'block';
    },

    closeModal() {
        this.modal.style.display = 'none';
        this.form.reset();
        this.imagePreview.innerHTML = '';
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

    // submit logic
    async handleSubmit(e) {
        e.preventDefault();
        
        try {
            const formData = new FormData(this.form);
            const token = localStorage.getItem('token');

            // new clothing piece
            const clothingPiece = {
                name: formData.get('name'),
                category: formData.get('category'),
                imgUrl: ""
            };

            const createdClothingPiece = await userService.addClothingPiece(clothingPiece);
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
            await userService.updateClothingPiece(clothingPieceId, clothingPiece);

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
    }
};

// Initialize when document is loaded
document.addEventListener('DOMContentLoaded', () => ClothingModal.init());