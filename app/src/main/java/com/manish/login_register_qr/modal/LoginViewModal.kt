package com.manish.login_register_qr.modal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.manish.login_register_qr.database.service.LoginService

class LoginViewModal (

    private val loginService: LoginService
) : ViewModel() {

    var username by mutableStateOf("")
    var usernameError by mutableStateOf<String?>(null)

    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)

    var loginError by mutableStateOf<String?>(null)
        private set

    // Validate input fields
    fun isValid(): Boolean {
        var valid = true

        if (username.isBlank()) {
            usernameError = "Please enter the username"
            valid = false
        } else {
            usernameError = null
        }

        if (email.isBlank()) {
            emailError = "Please enter the email"
            valid = false
        } else {
            emailError = null
        }

        return valid
    }

    // Perform login
    suspend fun login(): Boolean {
        if (!isValid()) return false

        val user = loginService.getUser(username, email)

        return if (user != null) {
            loginError = null
            true
        } else {
            loginError = "User not found or incorrect credentials"
            false
        }
    }
    fun clearFields() {
        username = ""
        email = ""

        usernameError = null
        emailError = null
    }
}