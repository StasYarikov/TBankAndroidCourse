package com.example.mynotfirstproject.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.ActivityMainBinding
import com.example.mynotfirstproject.databinding.JokeItemBinding

class JokeViewHolder(
    private val binding: JokeItemBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.categoryField.text = joke.category
        binding.questionField.text = joke.question
        binding.answerField.text = joke.answer
        joke.picture?.let {
            binding.picture.setImageResource(joke.picture)
        }
    }
}