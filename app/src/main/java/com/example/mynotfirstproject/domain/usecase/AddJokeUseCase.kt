package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeTypes
import com.example.mynotfirstproject.domain.repository.JokesRepository

class AddJokeUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke(joke: JokeTypes) {
        jokesRepository.addJoke(joke)
    }
}