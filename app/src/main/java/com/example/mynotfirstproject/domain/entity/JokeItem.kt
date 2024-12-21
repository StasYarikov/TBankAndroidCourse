package com.example.mynotfirstproject.domain.entity

import com.example.mynotfirstproject.utils.enums.LabelEnum

data class JokeItem(
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val picture: Int,
    val label: LabelEnum,
)
