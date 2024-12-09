package com.example.mynotfirstproject.domain.entity

data class JokeItem(
    val id: Int = UNDEFINED_ID,
    val category: String,
    val setup: String,
    val delivery: String,
    val picture: Int? = null,
    val label: String? = null,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
