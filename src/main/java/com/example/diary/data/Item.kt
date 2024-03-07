package com.example.diary.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Item for diary database
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val year: Int,
    val month: Int,
    val day: Int,
    val title: String,
    val contents: String
)