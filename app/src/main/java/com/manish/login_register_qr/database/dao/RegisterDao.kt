package com.manish.login_register_qr.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manish.login_register_qr.database.entity.Register

@Dao
interface RegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(register: Register)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(register: List<Register>)

    @Query("SELECT * FROM Register LIMIT 1")
    suspend fun getFirstUser(): Register?

    @Query("SELECT * FROM Register")
    suspend fun getAllUsers(): List<Register>

    @Query("SELECT * FROM Register WHERE User_name = :username")
    suspend fun getUserByUsername(username: String): Register?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(register: Register): Long

    @Query("DELETE FROM Register")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteUser(register: Register)

    @Query("SELECT COUNT(*) FROM Register")
    suspend fun getAllUserCount(): Long
}