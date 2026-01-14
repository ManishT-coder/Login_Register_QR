package com.manish.login_register_qr.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.manish.login_register_qr.database.entity.Register

@Dao
interface LoginDao {

    @Query("SELECT * FROM Register WHERE User_name = :username AND Email_Id = :email LIMIT 1")
    suspend fun authenticate(username: String, email: String): Register?

    @Query("SELECT * FROM Register")
    suspend fun getAllRegisteredUsers(): List<Register>
}