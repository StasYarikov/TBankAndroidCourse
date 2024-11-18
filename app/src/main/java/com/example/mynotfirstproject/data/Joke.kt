package com.example.mynotfirstproject.data

import androidx.annotation.IdRes
import java.util.UUID

data class Joke(
    val id: String = UUID.randomUUID().toString(),
    val category: String,
    val question: String,
    val answer: String,
    @IdRes val picture: Int?
)
