package com.example.mynotfirstproject.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Joke(
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerialName("category")
    val category: String,
    @SerialName("setup")
    val setup: String,
    @SerialName("delivery")
    val delivery: String,
    @SerialName("picture")
    val picture: Int? = null,
    val label: String? = null
)