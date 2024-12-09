package com.example.mynotfirstproject.presentation.jokes_list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.databinding.JokeItemBinding

class MyJokesViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (Int) -> Unit,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Jokes, position: Int) {
        binding.categoryField.text = item.category
        binding.questionField.text = item.setup
        binding.answerField.text = item.delivery
        binding.picture.setImageResource(item.picture ?: R.drawable.warning)
        binding.label.text = (item.label) ?: "From the Internet"
        binding.idField.text = item.id.toString()

        binding.root.setOnClickListener {
            clickListener(position)
        }
    }
}