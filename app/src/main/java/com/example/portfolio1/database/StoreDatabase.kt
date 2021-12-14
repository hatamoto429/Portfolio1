package com.example.portfolio1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.portfolio1.database.daos.UserDao
import com.example.portfolio1.database.entities.*
import com.example.portfolio1.database.entities.User

@Database(
    entities = [
       User::class
    ],
    version = 1
)

abstract class StoreDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun deleteDao(): UserDao
}