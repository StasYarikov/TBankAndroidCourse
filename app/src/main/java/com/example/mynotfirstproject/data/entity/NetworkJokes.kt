package com.example.mynotfirstproject.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotfirstproject.utils.enums.LabelEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "networkJokes")
data class NetworkJokes(
    @SerialName("id")
    @PrimaryKey
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val setup: String,
    @SerialName("delivery")
    val delivery: String,
    val timestamp: Long = System.currentTimeMillis(),
    val picture: Int? = null,
    var label: String = LabelEnum.INTERNETJOKE.label,
)