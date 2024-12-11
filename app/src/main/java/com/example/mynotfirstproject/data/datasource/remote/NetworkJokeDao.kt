package com.example.mynotfirstproject.data.datasource.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotfirstproject.data.entity.NetworkJokes
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkJokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFromNetwork(networkJokes: List<NetworkJokes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(networkJokes: NetworkJokes)

    @Query("DELETE FROM networkJokes WHERE id = :jokeId")
    suspend fun deleteNetworkJokeById(jokeId: Int)

    @Query("SELECT * FROM networkJokes WHERE id = :jokeId LIMIT 1")
    fun getJokeById(jokeId: Int): Flow<NetworkJokes?>

    @Query("SELECT * FROM networkJokes")
    fun getAllNetworkJokes(): Flow<List<NetworkJokes>>

    @Query("DELETE FROM networkJokes")
    suspend fun deleteAllJokes()
}