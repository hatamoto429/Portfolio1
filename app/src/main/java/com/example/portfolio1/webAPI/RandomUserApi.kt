package com.example.portfolio1.webAPI


import com.example.portfolio1.database.entities.ApiUser
import io.ktor.client.HttpClient
import io.ktor.client.request.*


// JSON von der API bekommen  ?
// umgewandelte JSON benutzen und infos rausfiltern , in datenbank speichern

class RandomUserApi (private val client: HttpClient) : NetworkSource {

    suspend fun getApiUser(count: Int): ApiUser =
        client.get("https://randomuser.me/api/?results=10")


}