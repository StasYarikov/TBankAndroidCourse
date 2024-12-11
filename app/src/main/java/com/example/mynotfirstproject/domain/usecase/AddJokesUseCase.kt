package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeTypes
import com.example.mynotfirstproject.domain.repository.JokesRepository

class AddJokesUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke(jokes: List<JokeTypes>) {
        jokesRepository.addJokes(jokes)
    }
}