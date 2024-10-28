package com.example.mynotfirstproject.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.JokeItemBinding
import com.example.mynotfirstproject.recycler.JokeViewHolder

class JokeAdapter(): RecyclerView.Adapter<JokeViewHolder>() {

    private var data = emptyList<Joke>()

    fun setNewData(newData: List<Joke>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater)
        return JokeViewHolder(binding).apply {
            binding.root.setOnClickListener {
                handleJokeClick(parent.context, adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: JokeViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    private fun handleJokeClick(context: Context, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = data[position]
            Toast.makeText(context, "Hahaha: ${item.answer}", Toast.LENGTH_SHORT).show()
        }
    }

}