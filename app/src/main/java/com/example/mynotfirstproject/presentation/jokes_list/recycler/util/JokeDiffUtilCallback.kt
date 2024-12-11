package com.example.mynotfirstproject.presentation.jokes_list.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.mynotfirstproject.domain.entity.JokeTypes

class JokeDiffUtilCallback(
    private val oldList: List<JokeTypes>,
    private val newList: List<JokeTypes>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is JokeTypes.MyJokes && newItem is JokeTypes.MyJokes -> oldItem.data.id == newItem.data.id
            oldItem is JokeTypes.JokesFromNetwork && newItem is JokeTypes.JokesFromNetwork -> oldItem.data.id == newItem.data.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is JokeTypes.MyJokes && newItem is JokeTypes.MyJokes -> oldItem == newItem
            oldItem is JokeTypes.JokesFromNetwork && newItem is JokeTypes.JokesFromNetwork -> oldItem == newItem
            else -> false
        }
    }
}