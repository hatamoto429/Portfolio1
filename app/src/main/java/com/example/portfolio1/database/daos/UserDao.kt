package com.example.portfolio1.database.daos

import androidx.room.*
import com.example.portfolio1.database.entities.User
import kotlinx.coroutines.flow.Flow

    @Dao
    interface UserDao {

        //insert data to room database
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertToRoomDatabase(user: User) : Long

        @Transaction
        @Query("SELECT * FROM users_table ORDER BY userFirstname")
        fun getUser(): Flow<List<User>>

        @Transaction
        @Query("SELECT * FROM users_table WHERE sha256 = :id ORDER BY userFirstname DESC")
        fun getSingleUserDetails(id: String) : Flow<User>

        @Delete
        suspend fun deleteAllUsersDetails(user: User)

        @Insert()
        suspend fun insertUser(user: User)

        @Delete
        suspend fun deleteNote(user: User)


    }


