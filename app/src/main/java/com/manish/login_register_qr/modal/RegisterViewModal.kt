package com.manish.login_register_qr.modal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.manish.login_register_qr.database.entity.Register
import com.manish.login_register_qr.database.service.RegisterService

class RegisterViewModal (
    private val registerService: RegisterService
) : ViewModel() {

    var firstName by mutableStateOf("")
    var firstNameError by mutableStateOf<String?>(null)

    var middleName by mutableStateOf("")
    var middleNameError by mutableStateOf<String?>(null)

    var lastName by mutableStateOf("")
    var lastNameError by mutableStateOf<String?>(null)

    var username by mutableStateOf("")
    var usernameError by mutableStateOf<String?>(null)

    var phoneNo by mutableStateOf("")
    var phoneNoError by mutableStateOf<String?>(null)

    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)

    var registerError by mutableStateOf<String?>(null)
        private set

    // Validate input fields
    fun isValid(): Boolean {
        var valid = true

        if(firstName.isBlank()){
            firstNameError="Pls Enter the FirstName"
            valid = false
        }else{
            firstNameError=null
        }

        if(middleName.isBlank()){
            middleNameError="Pls Enter the middleName"
            valid = false
        }else{
            middleNameError=null
        }

        if(lastName.isBlank()){
            lastNameError="Pls Enter the lastName"
            valid = false
        }else{
            lastNameError=null
        }

        if(username.isBlank()){
            usernameError="Pls Enter the username"
            valid = false
        }else{
            usernameError=null
        }

        if(phoneNo.isBlank()){
            phoneNoError="Pls Enter the FirstName"
            valid = false
        }else{
            firstNameError=null
        }

        if(firstName.isBlank()){
            firstNameError="Pls Enter the phoneNo"
            valid = false
        }else{
            phoneNoError=null
        }
        if(email.isBlank()){
            emailError="Pls Enter the email Id"
            valid = false
        }else{
            emailError=null
        }

        return valid
    }

    // Register user
    suspend fun register(): Boolean {
        if (!isValid()) return false

        val result = registerService.saveOrUpdate(
            Register(
                id = 0,
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                username = username,
                email = email,
                phoneNo = phoneNo
            )
        )

        return if (result>0) {
            registerError = null
            true
        } else {
            registerError = "Registration failed"
            false
        }


    }
    fun clearFields() {
        firstName = ""
        middleName = ""
        lastName = ""
        username = ""
        phoneNo = ""
        email = ""

        firstNameError = null
        middleNameError = null
        lastNameError = null
        usernameError = null
        phoneNoError = null
        emailError = null
    }

}