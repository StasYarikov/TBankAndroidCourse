package com.example.mynotfirstproject.data.mapper

import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.utils.enums.LabelEnum

class JokeItemJokesMapper {

    fun map(joke: Jokes) : JokeItem {
        return with(joke) {
            JokeItem(
                id = id,
                category = category,
                setup = setup,
                delivery = delivery,
                picture = picture ?: JokeGeneratorImpl.generateRandomPicture(),
                label = LabelEnum.LOCALJOKE,
            )
        }
    }

    fun mapToJokesFromJokeItem(item: JokeItem): Jokes {
        return with(item) {
            Jokes(
                id = id,
                category = category,
                setup = setup,
                delivery = delivery,
                picture = picture,
                label = label.label,
            )
        }
    }
}