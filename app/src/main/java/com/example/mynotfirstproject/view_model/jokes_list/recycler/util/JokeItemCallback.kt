package com.example.mynotfirstproject.view_model.jokes_list.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.mynotfirstproject.data.NetworkJokes

class JokeItemCallback: DiffUtil.ItemCallback<NetworkJokes>() {
    override fun areItemsTheSame(oldItem: NetworkJokes, newItem: NetworkJokes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NetworkJokes, newItem: NetworkJokes): Boolean {
        return oldItem == newItem
    }

}