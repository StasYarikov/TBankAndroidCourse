package com.example.mynotfirstproject.data.mapper

import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes
import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.example.mynotfirstproject.domain.entity.JokeItem

class JokeItemNetworkJokesMapper {

    fun map(joke: NetworkJokes) : JokeItem {
        return with(joke) {
            JokeItem(
                id = id,
                category = category,
                setup = setup,
                delivery = delivery,
                picture = picture ?: JokeGeneratorImpl.generateRandomPicture(),
                label = label ?: "From the Internet",
            )
        }
    }

    fun mapToNetworkJokesFromJokeItem(item: JokeItem): NetworkJokes {
        return with(item) {
            NetworkJokes(
                id = id,
                category = category,
                setup = setup,
                delivery = delivery,
                picture = picture,
                label = label,
            )
        }
    }
}