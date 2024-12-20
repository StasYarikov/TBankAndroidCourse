package com.example.mynotfirstproject.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class Jokes(
    @PrimaryKey
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val picture: Int? = null,
    val label: String? = null
)