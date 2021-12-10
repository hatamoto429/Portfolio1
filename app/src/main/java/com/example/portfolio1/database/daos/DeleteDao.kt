package com.example.portfolio1.database.daos

import androidx.room.Dao
import androidx.room.Delete

import com.example.portfolio1.database.entities.User

class DeleteDao {

    @Dao
    interface DeleteDao {
        @Delete
        fun deleteDao(user: User)
    }

}