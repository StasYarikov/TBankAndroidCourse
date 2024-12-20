package com.example.mynotfirstproject.domain.mapper

import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.presentation.uientity.JokeUI

class JokeUIJokeItemMapper {

    fun map(joke: JokeItem) : JokeUI {
        return with(joke) {
            JokeUI(
                id = id,
                category = category,
                setup = setup,
                delivery = delivery,
                picture = picture,
                label = label,
            )
        }
    }

    fun mapToJokeItemFromJokeItem(item: JokeUI): JokeItem {
        return with(item) {
            JokeItem(
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