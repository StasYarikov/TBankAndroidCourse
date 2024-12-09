package com.example.mynotfirstproject.presentation.jokes_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.presentation.JokeActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.JokeTypes
import com.example.mynotfirstproject.databinding.JokeListFragmentBinding
import com.example.mynotfirstproject.presentation.add_joke.AddJokeFragment
import com.example.mynotfirstproject.presentation.joke_details.JokeDetailsFragment
import com.example.mynotfirstproject.presentation.jokes_list.recycler.adapter.JokeAdapter
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

        binding.deleteAllJokes.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.deleteAllJokes()
                adapter.setNewData(emptyList())
            }
        }
//        viewModel.generateJokes()
        viewModel.loadJokesWithDelay()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jokesFlow.collect { jokes ->
                    if (jokes.isEmpty() && viewModel.networkJokesFlow.value.isEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                        val jokeTypesList: MutableList<JokeTypes> = jokes.map { JokeTypes.MyJokes(it) }.toMutableList()
                        if (viewModel.networkJokesFlow.value.isNotEmpty())
                            viewModel.updateNetworkJokes()
                        jokeTypesList.addAll(viewModel.networkJokesFlow.value.map { JokeTypes.JokesFromNetwork(it) })
                        adapter.setNewData(jokeTypesList)
                        adapter.notifyItemRangeChanged(0, jokeTypesList.size)
                    }
                }
            }
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner) { progressBar ->
            viewModel.loadingProcess = progressBar
            binding.progressBar.isVisible = progressBar
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            showError(error)
            val jokeTypesList: MutableList<JokeTypes> = viewModel.jokesFlow.value.map { JokeTypes.MyJokes(it) }.toMutableList()
            jokeTypesList.addAll(viewModel.networkJokesFlow.value.map { JokeTypes.JokesFromNetwork(it) })
            Log.d("Checking", "is here")
            adapter.setNewData(jokeTypesList)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            if (totalItemCount == lastVisibleItemPosition + 1) {
                Log.d("Checking", "$totalItemCount   $lastVisibleItemPosition    ${viewModel.loadingProcess}")
                if (!viewModel.loadingProcess) {
                    viewLifecycleOwner.lifecycleScope.launch(viewModel.exceptionHandler) {
                        viewModel.loadMoreJokes()
                        val jokeTypesList: MutableList<JokeTypes> = viewModel.jokesFlow.value.map { JokeTypes.MyJokes(it) }.toMutableList()
                        jokeTypesList.addAll(viewModel.networkJokesFlow.value.map { JokeTypes.JokesFromNetwork(it) })
                        adapter.setNewData(jokeTypesList)
                    }
                }
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
        Log.d("Checking", "$jokeId")
        val localJokesSize = viewModel.jokesFlow.value.size
        val realJokeId : Int
        if (localJokesSize > jokeId) {
            realJokeId = viewModel.jokesFlow.value[jokeId].id
        }
        else {
            realJokeId = viewModel.networkJokesFlow.value[jokeId - localJokesSize].id
        }
        val fragment = JokeDetailsFragment.newInstance(realJokeId)
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
