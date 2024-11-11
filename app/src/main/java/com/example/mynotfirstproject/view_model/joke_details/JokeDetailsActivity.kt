package com.example.mynotfirstproject.view_model.joke_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.ActivityJokeDetailsBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory

class JokeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokeDetailsBinding
    private lateinit var viewModel: JokeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        val jokePosition = intent.getIntExtra(JOKE_POSITION_EXTRA, -1)
        viewModel.setJokePosition(jokePosition)
    }

    private fun initViewModel() {
        val factory = JokesViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[JokeDetailsViewModel::class.java]
        viewModel.joke.observe(this) { joke -> setupJokeData(joke) }
        viewModel.error.observe(this) { showError(it) }

    }

    private fun setupJokeData(joke: Joke) {
        with (binding) {
            jokeAvatar.setImageResource(joke.picture ?: R.drawable.warning)
            jokeCategory.text = joke.category
            jokeQuestion.text = joke.question
            jokeAnswer.text = joke.answer
        }
    }

    private fun showError(it: String?) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {

        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun getInstance(context: Context, jokePosition: Int): Intent {
            return Intent(context, JokeDetailsActivity::class.java).apply {
                putExtra(JOKE_POSITION_EXTRA, jokePosition)
            }
        }
    }
}