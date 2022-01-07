package com.example.portfolio1.webAPI


import com.example.portfolio1.database.entities.Welcome
import io.ktor.client.*
import io.ktor.client.request.*

private const val BaseUrl: String = "https://randomuser.me/api"

// JSON von der API bekommen  ?
// umgewandelte JSON benutzen und infos rausfiltern , in datenbank speichern

class randomUserAPI (private val client: HttpClient, private val resultCount : Int ) {
    suspend fun get(paragraphCount: Int ): Welcome =
        client.get("$BaseUrl/?results=$paragraphCount")
}