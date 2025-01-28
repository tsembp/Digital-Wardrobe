console.log("auth-handlers.js loaded successfully!");
import { authService } from "./auth.service.js";

export const initializeAuthHandlers = () => {
  console.log("auth-handlers.js loaded successfully!");

  const loginForm = document.getElementById("loginForm");
  if (loginForm) {
    console.log("Login form found!");

    // Create error message element if it doesn't exist
    let errorElement = document.querySelector(".error-message");
    if (!errorElement) {
      errorElement = document.createElement("div");
      errorElement.className = "error-message";
      errorElement.style.color = "red";
      errorElement.style.marginTop = "10px";
      errorElement.style.fontSize = "14px";
      errorElement.style.display = "none"; // Initially hidden
      loginForm.appendChild(errorElement);
    }

    // Handle form submission
    loginForm.addEventListener("submit", async (e) => {
      console.log("Form submitted!"); // Debug log
      e.preventDefault(); // Prevent default form submission

      errorElement.textContent = ""; // Clear any previous error message
      errorElement.style.display = "none";

      // Collect input values
      const username = document.getElementById("username").value.trim();
      const password = document.getElementById("password").value.trim();

      if (!username || !password) {
        errorElement.textContent =
          "Please fill out both username and password.";
        errorElement.style.display = "block";
        return;
      }

      try {
        // Authenticate the user
        console.log("Sending login request...");
        const response = await authService.login(username, password);

        // Debugging response
        console.log("Login response from API:", response);

        // If login is successful, store the token and redirect
        if (response.token) {
          localStorage.setItem("token", response.token); // Store token in local storage
          console.log("Login successful. Redirecting to profile...");
          window.location.href = "/profile"; // Redirect to profile.html
        }
      } catch (error) {
        // Display error message
        console.error("Login error:", error);
        errorElement.textContent =
          error.message || "An unexpected error occurred. Please try again.";
        errorElement.style.display = "block";
      }
    });
  } else {
    console.error("Login form not found!");
  }
};
