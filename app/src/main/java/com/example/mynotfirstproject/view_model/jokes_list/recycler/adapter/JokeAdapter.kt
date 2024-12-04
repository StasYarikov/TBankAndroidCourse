package com.example.mynotfirstproject.view_model.jokes_list.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.data.JokeTypes
import com.example.mynotfirstproject.databinding.JokeItemBinding
import com.example.mynotfirstproject.view_model.jokes_list.recycler.JokesFromInternetViewHolder
import com.example.mynotfirstproject.view_model.jokes_list.recycler.MyJokesViewHolder
import com.example.mynotfirstproject.view_model.jokes_list.recycler.util.JokeDiffUtilCallback

class JokeAdapter(
    private val clickListener: (Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = emptyList<JokeTypes>()

    fun setNewData(newData: List<JokeTypes>) {
        val diffUtilCallback = JokeDiffUtilCallback(data, newData)
        val calculateDiff = DiffUtil.calculateDiff(diffUtilCallback)
        data = newData
        calculateDiff.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is JokeTypes.MyJokes -> MY_JOKES
            is JokeTypes.JokesFromNetwork -> JOKES_FROM_INTERNET
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MY_JOKES -> {
                val binding = JokeItemBinding.inflate(inflater, parent, false)
                MyJokesViewHolder(binding, clickListener)
            }
            JOKES_FROM_INTERNET -> {
                val binding = JokeItemBinding.inflate(inflater, parent, false)
                JokesFromInternetViewHolder(binding, clickListener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = data[position]) {
            is JokeTypes.MyJokes -> (holder as MyJokesViewHolder).bind(item.data, position)
            is JokeTypes.JokesFromNetwork -> (holder as JokesFromInternetViewHolder).bind(item.data, position)
        }
    }

    companion object {
        const val MY_JOKES = 1
        const val JOKES_FROM_INTERNET = 2
    }
}