package com.example.mynotfirstproject.data

import androidx.annotation.IdRes

data class Joke(
    val id: Int,
    val category: String,
    val question: String,
    val answer: String,
    @IdRes val picture: Int?
)
