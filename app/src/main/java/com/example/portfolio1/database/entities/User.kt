package com.example.portfolio1.database.entities

import android.media.Image
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

@Keep
@Serializable
@Entity(tableName = "AllUser")
data class UserResults (
    @SerialName("firstname")
    val userFirstname: String? = null,
    @SerialName("lastname")
    val userLastname: String? = null,
    @SerialName("picture")
    val userImage: String? = null,
    @SerialName("birthday")
    val userBirthday: String? = null,
    @PrimaryKey
    @SerialName("telephone")
    val userTelephone: String,
    )

