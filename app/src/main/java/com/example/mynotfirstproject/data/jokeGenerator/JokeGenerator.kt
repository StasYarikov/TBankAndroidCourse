package com.example.mynotfirstproject.data.jokeGenerator

import com.example.mynotfirstproject.data.entity.Jokes

interface JokeGenerator {

    fun generateJokes(): List<Jokes>


}