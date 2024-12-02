package com.example.mynotfirstproject.view_model.jokes_list.recycler

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
        binding.questionField.text = joke.setup
        binding.answerField.text = joke.delivery
        binding.picture.setImageResource(joke.picture ?: R.drawable.warning)
        binding.label.text = (joke.label) ?: "From the Internet"

        binding.root.setOnClickListener() {
            clickListener(position)
        }
    }
}