package com.manish.login_register_qr.database.service

import android.content.Context
import com.manish.login_register_qr.database.AppDatabase
import com.manish.login_register_qr.database.entity.Register

class RegisterService(context: Context) {

    private val dao = AppDatabase.getInstance(context).registerDao()

    suspend fun saveOrUpdate(register: Register): Long {
        return dao.saveOrUpdate(register)
    }

    suspend fun getConfig(): Register? {
        return dao.getFirstUser()
    }

    suspend fun getAllUser(): List<Register> {
        return dao.getAllUsers()
    }

    suspend fun getUser(username: String): Register? {
        return dao.getUserByUsername(username)
    }
}