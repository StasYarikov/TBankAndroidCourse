package com.example.mynotfirstproject.data

import com.example.mynotfirstproject.R

class JokeGenerator {

    private val jokes = arrayOf(
        arrayOf("Kids", "What do kids play when their mom is using the phone?", "Bored games"),
        arrayOf("Wildlife", "Why are snails slow?", "Because they’re carrying a house on their back."),
        arrayOf("Technology", "What is fast, loud and crunchy?", "A rocket chip."),
        arrayOf("Nature", "How does the ocean say hi?", "It waves!"),
        arrayOf("Nature", "Name the kind of tree you can hold in your hand?", "A palm tree!"),
        arrayOf("Music", "Where did the music teacher leave her keys?", "In the piano!"),
        arrayOf("Body", "What did the left eye say to the right eye?", "Between us, something smells!")
    )

    private val avatarSet = mutableSetOf(
        null,
        R.drawable.joking,
        R.drawable.laugh
    )

    fun generateJokes(): List<Joke> {
        return buildList {
            for (i in 0..6) {
                add(generateJoke(i))
            }
        }
    }

    private fun generateJoke(index: Int): Joke {
        return Joke(
            id = index,
            category = jokes[index][0],
            question = jokes[index][1],
            answer = jokes[index][2],
            picture = generateRandomPicture()
        )
    }

    private fun generateRandomPicture(): Int? {
        return avatarSet.random()
    }
}