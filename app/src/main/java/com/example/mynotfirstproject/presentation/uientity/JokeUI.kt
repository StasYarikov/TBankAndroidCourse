package com.example.mynotfirstproject.presentation.uientity

import com.example.mynotfirstproject.utils.enums.LabelEnum

data class JokeUI(
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val picture: Int,
    val label: LabelEnum,
)