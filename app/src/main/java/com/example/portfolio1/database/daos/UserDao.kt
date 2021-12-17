package com.example.portfolio1.database.daos

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.portfolio1.database.entities.User

import kotlinx.coroutines.flow.Flow

    @Dao
    interface UserDao {

        @Query("SELECT * FROM user")
        fun getUser(): Flow<List<User>>

        @Insert()
        suspend fun insertUser(user: User)

        @Delete
        suspend fun deleteNote(user: User)


    }


