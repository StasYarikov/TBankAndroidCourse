package com.example.mynotfirstproject.jokes_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        setNewDataToAdapter()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
    }

    private fun setNewDataToAdapter() {
        val data = generator.generateJokes()
        adapter.setNewData(data)
    }
}