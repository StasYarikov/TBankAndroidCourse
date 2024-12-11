package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeTypes
import com.example.mynotfirstproject.domain.repository.JokesRepository

class GetJokeByIdUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke(jokesId: Int) : JokeTypes = jokesRepository.getJokeById(jokesId)
}