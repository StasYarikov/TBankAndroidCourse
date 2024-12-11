package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.domain.repository.JokesRepository

class LoadMoreJokesUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke() : JokeApiResponse = jokesRepository.loadMoreJokes()
}