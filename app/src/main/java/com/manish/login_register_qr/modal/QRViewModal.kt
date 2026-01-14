package com.manish.login_register_qr.modal

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class QRViewModal: ViewModel() {

    var empID by mutableStateOf<Int?>(null)
    var firstName by mutableStateOf("")
    var middleName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNo by mutableStateOf("")
    var email by mutableStateOf("")

    var emperror by mutableStateOf<String?>(null)
    var firstnameerror by mutableStateOf<String?>(null)
    var middlenameerror by mutableStateOf<String?>(null)
    var lastnameerror by mutableStateOf<String?>(null)
    var phonenoerror by mutableStateOf<String?>(null)
    var emailerror by mutableStateOf<String?>(null)

    var qrBitmap by mutableStateOf<Bitmap?>(null)

    fun isValid(): Boolean {
        var valid = true

        if(firstName.isBlank()){
            firstnameerror="Pls Enter the FirstName"
            valid = false
        }else{
            firstnameerror=null
        }

        if(middleName.isBlank()){
            middlenameerror="Pls Enter the middleName"
            valid = false
        }else{
            middlenameerror=null
        }

        if(lastName.isBlank()){
            lastnameerror="Pls Enter the lastName"
            valid = false
        }else{
            lastnameerror=null
        }

        if(empID==null){
            emperror="Pls Enter the EmployeeID"
            valid = false
        }else{
            emperror=null
        }

        if(phoneNo.isBlank()){
            phonenoerror="Pls Enter the PhoneNo"
            valid = false
        }else{
            phonenoerror=null
        }
        if(email.isBlank()){
            emailerror="pls Enter the EmailId"
        }else{
            emailerror=null
        }

        return valid
    }
}

