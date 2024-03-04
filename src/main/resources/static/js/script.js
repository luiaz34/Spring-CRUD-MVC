
    document.addEventListener('DOMContentLoaded', function() {
      const createAccountLink = document.getElementById('createAccountLink');
      const backToLoginLink = document.getElementById('backToLogin');
      const registerForm = document.getElementById('registerContainer');
      const loginForm = document.getElementById('login-form-wrap');
      
      // redirect to registerForm
      createAccountLink.addEventListener('click', function(event){
        event.preventDefault(); 
        registerForm.style.display = 'block'; 
        loginForm.style.display = 'none';
      });
      
      // redirect back to loginForm
      backToLoginLink.addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = '/login'; 
      });
    });
     // Function to validate password
  function validatePassword() {
    const passwordInput = document.getElementById("reg-password");
    const password = passwordInput.value;
    // Define your password requirements
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumbers = /\d/.test(password);
    const hasSpecialChars = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+/.test(password);

    // Check if all requirements are met
    if (password.length < minLength) {
      displayError("Password must be at least 8 characters long");
    } else if (!hasUpperCase) {
      displayError("Password must contain at least one uppercase letter");
    } else if (!hasLowerCase) {
      displayError("Password must contain at least one lowercase letter");
    } else if (!hasNumbers) {
      displayError("Password must contain at least one number");
    } else if (!hasSpecialChars) {
      displayError("Password must contain at least one special character");
    } else {
      // Clear any previous error messages
      clearError();
    }
  }

  // Function to display error message
  function displayError(message) {
    const errorSpan = document.getElementById("password-error");
    errorSpan.textContent = message;
  }

  // Function to clear error message
  function clearError() {
    const errorSpan = document.getElementById("password-error");
    errorSpan.textContent = "";
  }

  // Attach password validation function to password input's form
  const passwordInput = document.getElementById("reg-password");
  passwordInput.addEventListener("input", validatePassword);
  