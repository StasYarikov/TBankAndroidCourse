package com.example.mynotfirstproject.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Joke(
    @SerialName("id")
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val setup: String,
    @SerialName("delivery")
    val delivery: String,
    val picture: Int? = null,
    val label: String? = null
)