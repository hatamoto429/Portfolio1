package com.example.portfolio1.webAPI


import com.example.portfolio1.database.entities.Welcome
import io.ktor.client.HttpClient
import io.ktor.client.request.*


// JSON von der API bekommen  ?
// umgewandelte JSON benutzen und infos rausfiltern , in datenbank speichern

class randomUserAPI (private val client: HttpClient) {
    suspend fun get(paragraphCount: Int ): Welcome =
        client.get("https://randomuser.me/api/?results=10")
}