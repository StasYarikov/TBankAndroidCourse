package com.example.mynotfirstproject.data

import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getJokes(
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10
    ): List<JokeResponse>
}