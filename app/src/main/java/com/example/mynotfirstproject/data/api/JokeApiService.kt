package com.example.mynotfirstproject.data.api

import com.example.mynotfirstproject.data.JokeApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any?")
    suspend fun getJokes(
        @Query("blacklistFlags") blacklistFlags: String = "nsfw,religious,political,racist,sexist,explicit",
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10,
    ) : JokeApiResponse
}