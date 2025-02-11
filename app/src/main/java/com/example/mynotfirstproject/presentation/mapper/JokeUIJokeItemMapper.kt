package com.example.mynotfirstproject.presentation.mapper

import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.utils.enums.LabelEnum
import com.example.mynotfirstproject.presentation.uientity.JokeUI
import javax.inject.Inject

class JokeUIJokeItemMapper @Inject constructor() {

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

    fun mapToJokeItemFromJokeUI(item: JokeUI): JokeItem {
        return with(item) {
            JokeItem(
                id = id,
                category = category,
                setup = setup,
                delivery = delivery,
                picture = picture,
                label = if (label == LabelEnum.INTERNETJOKE) LabelEnum.CACHEJOKE else LabelEnum.LOCALJOKE,
            )
        }
    }
}