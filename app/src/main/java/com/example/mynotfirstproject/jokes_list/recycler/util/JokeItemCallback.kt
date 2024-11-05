package com.example.mynotfirstproject.jokes_list.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.mynotfirstproject.data.Joke

class JokeItemCallback: DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }

}