package com.example.portfolio1.database.daos

import androidx.room.Dao
import androidx.room.Insert

import com.example.portfolio1.database.entities.User

class InsertDao {

    @Dao
    interface InsertDao {
        @Insert
        fun insertDao(user: User)
    }

}