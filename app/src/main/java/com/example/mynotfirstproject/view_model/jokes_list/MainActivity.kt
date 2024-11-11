package com.example.mynotfirstproject.view_model.jokes_list

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.ActivityMainBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsActivity
import com.example.mynotfirstproject.view_model.jokes_list.recycler.adapter.JokeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: JokeListViewModel

    private val adapter = JokeAdapter() {
        startActivity(JokeDetailsActivity.getInstance(this, it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createRecyclerViewList()
        initViewModel()

        if (savedInstanceState == null) {
            setNewDataToAdapter()
        }

        savedInstanceState?.let {
            viewModel.showGeneratedData()
        }
    }

    private fun initViewModel() {
        val factory = JokesViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[JokeListViewModel::class.java]

        viewModel.jokes.observe(this) { adapter.setNewData(it) }
        viewModel.error.observe(this) { showError(it) }

    }

    private fun showError(it: String?) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
    }

    private fun setNewDataToAdapter() {
        viewModel.generateJokes()
    }
}