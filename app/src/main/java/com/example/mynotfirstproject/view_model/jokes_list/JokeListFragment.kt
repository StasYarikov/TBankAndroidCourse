package com.example.mynotfirstproject.view_model.jokes_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.JokeActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.databinding.JokeListFragmentBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory
import com.example.mynotfirstproject.view_model.add_joke.AddJokeFragment
import com.example.mynotfirstproject.view_model.add_joke.DeleteJokeFragment
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsFragment
import com.example.mynotfirstproject.view_model.jokes_list.recycler.adapter.JokeAdapter

class JokeListFragment : Fragment() {

    private var _binding: JokeListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokeListViewModel by activityViewModels {
        (requireActivity() as JokeActivity).viewModelFactory
    }

    private val adapter = JokeAdapter { jokeId ->
        openJokeDetails(jokeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = JokeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        createRecyclerViewList()

        if (viewModel.jokes.value == null) {
            viewModel.generateJokes()
        }
        binding.addJoke.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddJokeFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.deleteJoke.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DeleteJokeFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadJokesWithDelay()
    }

    private fun initViewModel() {

        viewModel.jokes.observe(viewLifecycleOwner) { jokes ->
            if (jokes.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
                adapter.setNewData(jokes)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            showError(error)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Unknown error", Toast.LENGTH_SHORT).show()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
    }

    private fun openJokeDetails(jokeId: Int) {
        val fragment = JokeDetailsFragment.newInstance(jokeId)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
