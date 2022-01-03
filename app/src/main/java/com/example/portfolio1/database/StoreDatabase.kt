package com.example.portfolio1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.portfolio1.database.daos.UserDao
import com.example.portfolio1.database.entities.Converters
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.global.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
       User::class
    ],
    version = 1
)

@TypeConverters(Converters::class)

abstract class StoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    class Callback @Inject constructor(private val songDatabase: Provider<StoreDatabase>, @ApplicationScope private val applicationScope: CoroutineScope) : androidx.room.RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = songDatabase.get().userDao()
            applicationScope.launch {
            }
        }
    }
}