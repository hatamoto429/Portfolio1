package com.example.portfolio1.repository

import com.example.portfolio1.database.daos.UserDao
import com.example.portfolio1.database.entities.User
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    //insert user details to room
    suspend fun createUserRecords(user: User) : Long {
        return userDao.insertToRoomDatabase(user)
    }

    //get single user details e.g with id 1
    val getUsers: Flow<List<User>> get() =  userDao.getUsers()

    suspend fun getSingleUser(shaKey :String) {
        userDao.getSingleUserDetails(shaKey)
    }

    //delete single user record
    suspend fun deleteSingleUserRecord(shaKey :String) {
        userDao.deleteSingleUserDetails(shaKey)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsersDetails()
    }
}
