package com.manish.login_register_qr.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Register")
data class Register(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("First_name") @ColumnInfo(name = "First_name") val firstName: String,
    @SerializedName("Middle_name") @ColumnInfo(name = "Middle_name") val middleName: String,
    @SerializedName("Last_Name") @ColumnInfo(name = "Last_Name") val lastName: String,
    @SerializedName("User_name") @ColumnInfo(name = "User_name") val username: String,
    @SerializedName("Email_Id") @ColumnInfo(name = "Email_Id") val email: String,
    @SerializedName("Phone_No") @ColumnInfo(name = "Phone_No") val phoneNo: String
)