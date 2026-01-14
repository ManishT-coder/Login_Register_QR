package com.manish.login_register_qr.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Login")
data class Login(

    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("User_name") @ColumnInfo(name = "User_name") val username: String,
    @SerializedName("Email_Id") @ColumnInfo(name = "Email_Id") val email: String,

    )