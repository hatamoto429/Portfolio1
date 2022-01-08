package com.example.portfolio1.database.entities

import android.media.Image
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
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

@Entity(tableName = "users_table")
data class User (
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val sha256 : String,
    val title : String,
    val userFirstname: String,
    val userLastname: String,
    val pictureMedium : String,
    val pictureSmall : String,
    var userBirthday: String,
    val userTelephone: String,
    )