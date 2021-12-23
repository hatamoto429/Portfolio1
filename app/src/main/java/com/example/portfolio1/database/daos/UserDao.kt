package com.example.portfolio1.database.daos

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.portfolio1.database.entities.UserResults

import kotlinx.coroutines.flow.Flow

    @Dao
    interface UserDao {

        @Query("SELECT * FROM AllUser")
        fun getUser(): Flow<List<UserResults>>

        @Insert()
        suspend fun insertUser(user: UserResults)

        @Delete
        suspend fun deleteNote(user: UserResults)


    }


