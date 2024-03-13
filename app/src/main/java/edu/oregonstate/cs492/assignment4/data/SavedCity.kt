package edu.oregonstate.cs492.assignment4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedCity(
    @PrimaryKey
    val cityName: String,
    val timeStamp: Long
)
