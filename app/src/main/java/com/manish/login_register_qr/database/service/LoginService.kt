package com.manish.login_register_qr.database.service

import android.content.Context
import com.manish.login_register_qr.database.AppDatabase
import com.manish.login_register_qr.database.entity.Register

class LoginService(context: Context) {

    private val dao = AppDatabase.getInstance(context).loginDao()

    suspend fun getUser(username:String,email: String): Register?{
        return dao.authenticate(username,email)
    }

    suspend fun getAllRegisteredUsers(): List<Register> {
        return dao.getAllRegisteredUsers()
    }

}