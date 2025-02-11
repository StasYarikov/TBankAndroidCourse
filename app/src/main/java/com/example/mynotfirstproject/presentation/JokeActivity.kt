package com.example.mynotfirstproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotfirstproject.App
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.data.datasource.db.AppDatabase
import com.example.mynotfirstproject.data.datasource.db.implementations.LocalDataSourceImpl
import com.example.mynotfirstproject.data.datasource.db.implementations.RemoteDataSourceImpl
import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.example.mynotfirstproject.data.mapper.JokeItemJokesMapper
import com.example.mynotfirstproject.data.mapper.JokeItemNetworkJokesMapper
import com.example.mynotfirstproject.databinding.ActivityJokeBinding
import com.example.mynotfirstproject.presentation.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.presentation.viewModelFactory.JokesViewModelFactory
import com.example.mynotfirstproject.presentation.jokes_list.JokeListFragment
import javax.inject.Inject

class JokeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokeBinding

    @Inject
    lateinit var viewModelFactory: JokesViewModelFactory

    @Inject
    lateinit var repository: JokeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

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
