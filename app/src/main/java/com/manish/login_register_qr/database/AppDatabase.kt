package com.manish.login_register_qr.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manish.login_register_qr.database.dao.LoginDao
import com.manish.login_register_qr.database.dao.RegisterDao
import com.manish.login_register_qr.database.entity.Login
import com.manish.login_register_qr.database.entity.Register

@Database(
    entities = [Login::class, Register::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun registerDao(): RegisterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "TOM_DATABASE"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
        }
    }
}