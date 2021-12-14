// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json    = Json(JsonConfiguration.Stable)
// val welcome = json.parse(Welcome.serializer(), jsonString)

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Welcome (
    val results: List<Result>,
    val info: Info
)

@Serializable
data class Info (
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)

@Serializable
data class Result (
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Dob,
    val phone: String,
    val cell: String,
    val id: ID,
    val picture: Picture,
    val nat: String
)

@Serializable
data class Dob (
    val date: String,
    val age: Long
)

@Serializable
data class ID (
    val name: String,
    val value: String
)

@Serializable
data class Location (
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Long,
    val coordinates: Coordinates,
    val timezone: Timezone
)

@Serializable
data class Coordinates (
    val latitude: String,
    val longitude: String
)

@Serializable
data class Street (
    val number: Long,
    val name: String
)

@Serializable
data class Timezone (
    val offset: String,
    val description: String
)

@Serializable
data class Login (
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

@Serializable
data class Name (
    val title: String,
    val first: String,
    val last: String
)

@Serializable
data class Picture (
    val large: String,
    val medium: String,
    val thumbnail: String
)
