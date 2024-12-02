package com.example.mynotfirstproject.view_model.jokes_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.JokeActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.databinding.JokeListFragmentBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory
import com.example.mynotfirstproject.view_model.add_joke.AddJokeFragment
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsFragment
import com.example.mynotfirstproject.view_model.jokes_list.recycler.adapter.JokeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        binding.addJoke.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddJokeFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadJokesWithDelay()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jokesFlow.collectLatest { jokes ->
                    if (jokes.isEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                        adapter.setNewData(jokes)
                    }
                }
            }
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner) { progressBar ->
            viewModel.loadingProcess = !viewModel.loadingProcess
            binding.progressBar.isVisible = progressBar
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            showError(error)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            if (totalItemCount == lastVisibleItemPosition + 1) {
                if (!viewModel.loadingProcess)
                    viewModel.loadMoreJokes()
            }
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Unknown error", Toast.LENGTH_SHORT).show()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(scrollListener)
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
