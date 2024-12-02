package com.example.mynotfirstproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.data.db.AppDatabase
import com.example.mynotfirstproject.databinding.ActivityJokeBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory
import com.example.mynotfirstproject.view_model.jokes_list.JokeListFragment
import com.example.mynotfirstproject.view_model.jokes_list.JokeListViewModel

class JokeActivity : AppCompatActivity() {

    private val repository by lazy { JokeRepository(
        AppDatabase.INSTANCE.jokeDao()
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
