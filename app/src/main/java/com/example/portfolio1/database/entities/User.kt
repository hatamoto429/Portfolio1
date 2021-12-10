package com.example.portfolio1.database.entities

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity()

data class User (
    @PrimaryKey
    val userFirstname: String,
    val userLastname: String,
    val userPicture: Image,
    val userBirthday: Date,
    val userTelephone: Double,
    )