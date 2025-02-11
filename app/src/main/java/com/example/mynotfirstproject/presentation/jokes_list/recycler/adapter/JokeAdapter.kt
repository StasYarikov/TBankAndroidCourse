package com.example.mynotfirstproject.presentation.jokes_list.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.databinding.JokeItemBinding
import com.example.mynotfirstproject.presentation.jokes_list.recycler.JokeUIViewHolder
import com.example.mynotfirstproject.presentation.jokes_list.recycler.util.JokeDiffUtilCallback
import com.example.mynotfirstproject.presentation.uientity.JokeUI
import javax.inject.Inject

class JokeAdapter (
    private val clickListener: (Int) -> Unit
): RecyclerView.Adapter<JokeUIViewHolder>() {

    private var data = emptyList<JokeUI>()

    fun setNewData(newData: List<JokeUI>) {
        val diffUtilCallback = JokeDiffUtilCallback(data, newData)
        val calculateDiff = DiffUtil.calculateDiff(diffUtilCallback)
        data = newData
        calculateDiff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeUIViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)
        return JokeUIViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: JokeUIViewHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
    }
}