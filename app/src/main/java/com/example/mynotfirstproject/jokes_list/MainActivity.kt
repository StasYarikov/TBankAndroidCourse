package com.example.mynotfirstproject.jokes_list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.ActivityMainBinding
import com.example.mynotfirstproject.joke_details.JokeDetailsActivity
import com.example.mynotfirstproject.jokes_list.recycler.adapter.JokeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val generator = JokeGenerator
    private val adapter = JokeAdapter() {
        startActivity(JokeDetailsActivity.getInstance(this, it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createRecyclerViewList()
        if (savedInstanceState == null) {
            setNewDataToAdapter()
        }
        savedInstanceState?.let {
            setupAdapter(generator.data)
        }
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
    }

    private fun setNewDataToAdapter() {
        val data = generator.generateJokes()
        adapter.setNewData(data)
    }

    private fun setupAdapter(newData: List<Joke>) {
        adapter.setNewData(newData)
    }
}