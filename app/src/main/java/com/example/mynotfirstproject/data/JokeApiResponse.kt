package com.example.mynotfirstproject.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokeApiResponse(
    @SerialName("jokes")
    val networkJokes: List<NetworkJokes>
)
