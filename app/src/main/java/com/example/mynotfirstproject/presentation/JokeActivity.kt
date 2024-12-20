package com.example.mynotfirstproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.data.datasource.db.AppDatabase
import com.example.mynotfirstproject.data.datasource.db.implementations.LocalDataSourceImpl
import com.example.mynotfirstproject.data.datasource.db.implementations.RemoteDataSourceImpl
import com.example.mynotfirstproject.data.datasource.service.RetrofitInstance
import com.example.mynotfirstproject.data.mapper.JokeItemJokesMapper
import com.example.mynotfirstproject.data.mapper.JokeItemNetworkJokesMapper
import com.example.mynotfirstproject.databinding.ActivityJokeBinding
import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.presentation.viewModelFactory.JokesViewModelFactory
import com.example.mynotfirstproject.presentation.jokes_list.JokeListFragment

class JokeActivity : AppCompatActivity() {

    private val repository by lazy { JokeRepository(
        localDataSource = LocalDataSourceImpl(
            AppDatabase.INSTANCE.jokeDao()
        ),
        remoteDataSource = RemoteDataSourceImpl(
            networkJokeDao = AppDatabase.INSTANCE.networkDao(),
            api = RetrofitInstance.api
        ),
        jokeItemJokesMapper = JokeItemJokesMapper(),
        jokeItemNetworkJokesMapper = JokeItemNetworkJokesMapper(),
    ) }
    val viewModelFactory by lazy { JokesViewModelFactory(
        repository,
        jokeUIJokeItemMapper = JokeUIJokeItemMapper(),
    ) }
    private lateinit var binding: ActivityJokeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, JokeListFragment())
                .commit()
        }
    }
}
