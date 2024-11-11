package com.example.mynotfirstproject.jokes_list.recycler

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.JokeItemBinding

class JokeViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (Int) -> Unit,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke, position: Int) {
        binding.categoryField.text = joke.category
        binding.questionField.text = joke.question
        binding.answerField.text = joke.answer
        binding.picture.setImageResource(joke.picture ?: R.drawable.warning)

        binding.root.setOnClickListener() {
            clickListener(position)
        }
    }
}