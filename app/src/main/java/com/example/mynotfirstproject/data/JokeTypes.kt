package com.example.mynotfirstproject.data

import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes

sealed class JokeTypes {
    data class MyJokes(val data: Jokes) : JokeTypes()
    data class JokesFromNetwork(val data: NetworkJokes) : JokeTypes()
}