package com.example.mynotfirstproject.presentation.jokes_list.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.mynotfirstproject.presentation.uientity.JokeUI

class JokeDiffUtilCallback(
    private val oldList: List<JokeUI>,
    private val newList: List<JokeUI>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}