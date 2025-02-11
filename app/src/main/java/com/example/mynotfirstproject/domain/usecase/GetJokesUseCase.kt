package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.presentation.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository
import com.example.mynotfirstproject.presentation.uientity.JokeUI
import javax.inject.Inject

class GetJokesUseCase @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke() : List<JokeUI> = jokesRepository.getJokes().map {
        jokeUIJokeItemMapper.map(it)
    }
}