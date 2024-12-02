package com.example.mynotfirstproject.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotfirstproject.data.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(jokes: List<Joke>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: Joke)

    @Query("DELETE FROM jokes WHERE id = :jokeId")
    suspend fun deleteJokeById(jokeId: Int)

    @Query("SELECT * FROM jokes")
    fun getAllJokes(): Flow<List<Joke>>

}