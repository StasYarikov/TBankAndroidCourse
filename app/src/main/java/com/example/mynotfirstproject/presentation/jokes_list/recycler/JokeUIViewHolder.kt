package com.example.mynotfirstproject.presentation.jokes_list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.databinding.JokeItemBinding
import com.example.mynotfirstproject.presentation.uientity.JokeUI

class JokeUIViewHolder(
    private val binding: JokeItemBinding,
    private val clickListener: (Int) -> Unit,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: JokeUI, position: Int) {
        binding.categoryField.text = item.category
        binding.questionField.text = item.setup
        binding.answerField.text = item.delivery
        binding.picture.setImageResource(item.picture)
        binding.label.text = item.label
        binding.idField.text = item.id.toString()

        binding.root.setOnClickListener {
            clickListener(position)
        }
    }
}