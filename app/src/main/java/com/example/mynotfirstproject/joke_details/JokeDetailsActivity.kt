package com.example.mynotfirstproject.joke_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.ActivityJokeDetailsBinding

class JokeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokeDetailsBinding

    private val generator = JokeGenerator

    private var jokePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleExtra()
    }

    private fun handleExtra() {
        jokePosition = intent.getIntExtra(JOKE_POSITION_EXTRA, -1)

        if (jokePosition == -1) {
            handleError()
        } else {
            val item = generator.data[jokePosition] as? Joke
            if (item != null) {
                setupJokeData(item)
            } else {
                handleError()
            }
        }
    }

    private fun setupJokeData(joke: Joke) {
        with (binding) {
            jokeAvatar.setImageResource(joke.picture ?: R.drawable.warning)
            jokeCategory.text = joke.category
            jokeQuestion.text = joke.question
            jokeAnswer.text = joke.answer
        }
    }

    private fun handleError() {
        Toast.makeText(this, "Invalid joke data!", Toast.LENGTH_SHORT).show()
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