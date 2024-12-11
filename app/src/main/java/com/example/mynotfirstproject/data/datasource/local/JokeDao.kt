package com.example.mynotfirstproject.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotfirstproject.data.entity.Jokes
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(jokes: List<Jokes>)

    @Query("DELETE FROM jokes WHERE id = :jokeId")
    suspend fun deleteJokeById(jokeId: Int)

    @Query("SELECT * FROM jokes")
    fun getAllJokes(): Flow<List<Jokes>>

    @Query("SELECT * FROM jokes WHERE id = :jokeId LIMIT 1")
    fun getJokeById(jokeId: Int): Flow<Jokes?>

    @Query("DELETE FROM jokes")
    suspend fun deleteAllJokes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: Jokes)
}