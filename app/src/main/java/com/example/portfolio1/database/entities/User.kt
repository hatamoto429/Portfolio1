package com.example.portfolio1.database.entities

import android.media.Image
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

@Entity()
data class User (
    @PrimaryKey
    val userFirstname: String,
    val userLastname: String,
    //image
    val userBirthday: Date,
    val userTelephone: Double,
    )