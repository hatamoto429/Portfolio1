package com.example.portfolio1

import android.app.Application
import androidx.room.Room
import com.example.portfolio1.database.entities.StoreDatabase

class StoreApplication : Application() {

    lateinit var database: StoreDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room
            .databaseBuilder(
                applicationContext,
                StoreDatabase::class.java,
                "store-database-name"
            )
            .build()

        val userDao = database.userDao()

        //val user = listOf(

        //)

    }


}