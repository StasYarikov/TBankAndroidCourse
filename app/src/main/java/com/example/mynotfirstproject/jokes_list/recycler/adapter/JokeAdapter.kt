package com.example.mynotfirstproject.jokes_list.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.JokeItemBinding
import com.example.mynotfirstproject.jokes_list.recycler.JokeViewHolder
import com.example.mynotfirstproject.jokes_list.recycler.util.JokeDiffUtilCallback

class JokeAdapter(
    private val clickListener: (Int) -> Unit
): RecyclerView.Adapter<JokeViewHolder>() {

    private var data = emptyList<Joke>()

    fun setNewData(newData: List<Joke>) {
        val diffUtilCallback = JokeDiffUtilCallback(data, newData)
        val calculateDiff = DiffUtil.calculateDiff(diffUtilCallback)
        data = newData
        Log.d("Checking", "$newData")
        calculateDiff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater)
        return JokeViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: JokeViewHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
    }

}