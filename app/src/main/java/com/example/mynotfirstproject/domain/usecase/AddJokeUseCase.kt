package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository
import com.example.mynotfirstproject.presentation.uientity.JokeUI

class AddJokeUseCase(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke(joke: JokeUI) {
        jokesRepository.addJoke(jokeUIJokeItemMapper.mapToJokeItemFromJokeUI(joke))
    }
}