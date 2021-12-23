package com.example.portfolio1.webAPI

import com.example.portfolio1.database.entities.ApiUser

interface NetworkSource {

    suspend fun getUsers(resultCount: Int): ApiUser

}