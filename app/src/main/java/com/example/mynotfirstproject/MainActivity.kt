package com.example.mynotfirstproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.ActivityMainBinding
import com.example.mynotfirstproject.recycler.adapter.JokeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = JokeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerViewList()
        setNewDataToAdapter()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
    }

    private fun setNewDataToAdapter() {
        val generator = JokeGenerator()
        val data = generator.generateJokes()
        adapter.setNewData(data)
    }
}