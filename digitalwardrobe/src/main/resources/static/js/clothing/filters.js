document.addEventListener('DOMContentLoaded', () => {
    // populate dropdowns
    populateCategories();
    populateColors();

    // toggle show/hide dropdowns
    setupDropdownToggle('filterCategoryDropdown', 'filterCategoryList');
    setupDropdownToggle('filterColorDropdown', 'filterColorList');

    // apply/clear filters
    document.getElementById('applyFiltersBtn').addEventListener('click', applyFilters);
    document.getElementById('clearFiltersBtn').addEventListener('click', clearFilters);
});

function setupDropdownToggle(dropdownId, listId) {
    const dropdown = document.getElementById(dropdownId);
    const list = document.getElementById(listId);
    const button = dropdown.querySelector('.dropdown-btn');

    button.addEventListener('click', (event) => {
        event.stopPropagation();
        list.classList.toggle('show');
    });

    document.addEventListener('click', (event) => {
        if (!dropdown.contains(event.target)) {
            list.classList.remove('show');
        }
    });
}

function populateCategories() {
    const categories = [
        { name: "Shoes", icon: "/assets/category-icons/sneakers.png" },
        { name: "Shorts", icon: "/assets/category-icons/denim-shorts.png" },
        { name: "Skirts", icon: "/assets/category-icons/skirt.png" },
        { name: "Pants", icon: "/assets/category-icons/jeans.png" },
        { name: "Joggers", icon: "/assets/category-icons/jogger-pants.png" },
        { name: "T-Shirts", icon: "/assets/category-icons/tshirt.png" },
        { name: "Shirts", icon: "/assets/category-icons/hawaiian-shirt.png" },
        { name: "Hoodies", icon: "/assets/category-icons/hoodie.png" },
        { name: "Sweatshirts", icon: "/assets/category-icons/sweatshirt.png" },
        { name: "Jackets", icon: "/assets/category-icons/jacket.png" },
        { name: "Hats", icon: "/assets/category-icons/hat.png" },
        { name: "Belts", icon: "/assets/category-icons/belt.png" },
        { name: "Scarves", icon: "/assets/category-icons/scarf.png" },
        { name: "Bags", icon: "/assets/category-icons/handbag.png" },
        { name: "Glasses", icon: "/assets/category-icons/glasses.png" },
    ];

    const filterCategoryList = document.getElementById('filterCategoryList');

    categories.forEach(category => {
        const option = document.createElement('div');
        option.classList.add('dropdown-option');
        option.innerHTML = `
            <div>
                <img src="${category.icon}" alt="${category.name}">
                ${category.name}
            </div>
            <input type="checkbox" class="category-checkbox" value="${category.name}">
        `;
        filterCategoryList.appendChild(option);
    });
}

function populateColors() {
    const colors = [
        { name: 'Red', hex: 'red' },
        { name: 'Blue', hex: 'blue' },
        { name: 'Green', hex: 'green' },
        { name: 'Yellow', hex: 'yellow' },
        { name: 'Black', hex: 'black' },
        { name: 'White', hex: 'white' },
        { name: 'Gray', hex: 'gray' },
        { name: 'Purple', hex: '#A020F0' },
        { name: 'Orange', hex: 'orange' },
        { name: 'Pink', hex: 'pink' },
        { name: 'Brown', hex: '#964B00' },
    ];
    

    const filterColorList = document.getElementById('filterColorList');

    colors.forEach(color => {
        const option = document.createElement('div');
        option.classList.add('dropdown-option');
        option.innerHTML = `
            <div>
                <span class="color-circle" style="background-color: ${color.hex};"></span>
                ${color.name}
            </div>
            <input type="checkbox" class="color-checkbox" value="${color.name}">
        `;
        filterColorList.appendChild(option);
    });
}

function openFilterSection() {
    document.getElementById('filterSection').classList.add('open');
}

function closeFilterSection() {
    document.getElementById('filterSection').classList.remove('open');
}

function applyFilters() {
    const selectedCategories = Array.from(document.querySelectorAll('.category-checkbox:checked')).map(cb => cb.value.toUpperCase());
    const selectedColors = Array.from(document.querySelectorAll('.color-checkbox:checked')).map(cb => cb.value.toUpperCase());

    console.log('Selected categories:', selectedCategories);
    console.log('Selected colors:', selectedColors);
    
    const clothingCards = document.querySelectorAll('.clothing-card');
    let hasResults = false;
    console.log(clothingCards);
    
    clothingCards.forEach(card => {
        const category = card.getAttribute('data-category');
        const color = card.getAttribute('data-color');

        console.log(`Card category: ${category}, color: ${color}`);

        const matchesCategory = selectedCategories.length === 0 || selectedCategories.includes(category);
        const matchesColor = selectedColors.length === 0 || selectedColors.includes(color);

        if (matchesCategory && matchesColor) {
            card.style.display = 'block';
            hasResults = true;
        } else {
            card.style.display = 'none';
        }
    });

    const noMatchesMessage = document.getElementById('noMatchesMessage');
    if (!hasResults) {
        noMatchesMessage.style.display = 'block';
    } else {
        noMatchesMessage.style.display = 'none';
    }

    closeFilterSection();
}

function clearFilters() {
    document.querySelectorAll('.category-checkbox, .color-checkbox').forEach(cb => cb.checked = false);
    document.querySelectorAll('.clothing-card').forEach(card => card.style.display = 'block');
}