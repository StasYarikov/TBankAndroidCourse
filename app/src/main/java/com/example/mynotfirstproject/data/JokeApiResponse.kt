package com.example.mynotfirstproject.data

import androidx.annotation.IdRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokeApiResponse(
    @SerialName("jokes")
    val jokes: List<Joke>
)
