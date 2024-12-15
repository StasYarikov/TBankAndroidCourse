package com.example.mynotfirstproject.domain.entity

data class JokeItem(
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val picture: Int,
    val label: String,
)
