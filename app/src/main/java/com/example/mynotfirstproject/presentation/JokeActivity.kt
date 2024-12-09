package com.example.mynotfirstproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.data.db.AppDatabase
import com.example.mynotfirstproject.databinding.ActivityJokeBinding
import com.example.mynotfirstproject.presentation.viewModelFactory.JokesViewModelFactory
import com.example.mynotfirstproject.presentation.jokes_list.JokeListFragment

class JokeActivity : AppCompatActivity() {

    private val repository by lazy { JokeRepository(
        AppDatabase.INSTANCE.jokeDao(),
        AppDatabase.INSTANCE.networkDao()
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
