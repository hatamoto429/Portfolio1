package com.example.portfolio1.database.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.portfolio1.database.daos.UserDao
import com.example.portfolio1.database.entities.*
import com.example.portfolio1.database.entities.UserResults


@Database(
    entities = [
       UserResults::class
    ],
    version = 1
)

@TypeConverters(Converters::class)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
