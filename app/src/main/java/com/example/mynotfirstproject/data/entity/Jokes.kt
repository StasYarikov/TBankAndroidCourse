package com.example.mynotfirstproject.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotfirstproject.utils.enums.LabelEnum

@Entity(tableName = "jokes")
data class Jokes(
    @PrimaryKey
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val picture: Int? = null,
    val label: String = LabelEnum.LOCALJOKE.label,
)