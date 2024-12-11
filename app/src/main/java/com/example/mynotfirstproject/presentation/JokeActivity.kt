package com.example.mynotfirstproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.data.datasource.AppDatabase
import com.example.mynotfirstproject.data.datasource.local.LocalDataSource
import com.example.mynotfirstproject.data.datasource.local.LocalDataSourceImpl
import com.example.mynotfirstproject.data.datasource.remote.JokeApiService
import com.example.mynotfirstproject.data.datasource.remote.RemoteDataSourceImpl
import com.example.mynotfirstproject.data.datasource.remote.RetrofitInstance
import com.example.mynotfirstproject.databinding.ActivityJokeBinding
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
        )
    ) }
    val viewModelFactory by lazy { JokesViewModelFactory(repository) }
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
