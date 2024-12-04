package com.example.mynotfirstproject.data

sealed class JokeTypes {
    data class MyJokes(val data: Jokes) : JokeTypes()
    data class JokesFromNetwork(val data: NetworkJokes) : JokeTypes()
}