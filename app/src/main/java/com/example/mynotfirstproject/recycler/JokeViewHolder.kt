package com.example.mynotfirstproject.recycler

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.JokeItemBinding

class JokeViewHolder(
    private val binding: JokeItemBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke, position: Int, context: Context) {
        binding.categoryField.text = joke.category
        binding.questionField.text = joke.question
        binding.answerField.text = joke.answer
        binding.picture.setImageResource(joke.picture ?: R.drawable.warning)

        binding.root.setOnClickListener() {
            handleJokeClick(joke, context, position)
        }
    }

    private fun handleJokeClick(joke: Joke, context: Context, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            Toast.makeText(context, "Hahaha: ${joke.answer}", Toast.LENGTH_SHORT).show()
        }
    }
}