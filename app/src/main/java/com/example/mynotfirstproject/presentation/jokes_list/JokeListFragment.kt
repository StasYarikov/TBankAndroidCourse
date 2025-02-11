package com.example.mynotfirstproject.presentation.jokes_list

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.App
import com.example.mynotfirstproject.presentation.JokeActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.databinding.JokeListFragmentBinding
import com.example.mynotfirstproject.presentation.add_joke.AddJokeFragment
import com.example.mynotfirstproject.presentation.joke_details.JokeDetailsFragment
import com.example.mynotfirstproject.presentation.jokes_list.recycler.adapter.JokeAdapter
import com.example.mynotfirstproject.presentation.viewModelFactory.JokesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeListFragment : Fragment() {

    private var _binding: JokeListFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: JokesViewModelFactory
    private val viewModel: JokeListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[JokeListViewModel::class.java]
    }

    private val adapter = JokeAdapter { jokeId ->
        openJokeDetails(jokeId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as App).appComponent.inject(this)
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
                    if (jokes.isEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                        adapter.setNewData(jokes)
                        adapter.notifyItemRangeChanged(0, jokes.size)
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
            adapter.setNewData(viewModel.jokesFlow.value)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            Log.d("Check", "$totalItemCount $lastVisibleItemPosition")
            if (totalItemCount == lastVisibleItemPosition + 1) {
                if (!viewModel.loadingProcess) {
                    viewLifecycleOwner.lifecycleScope.launch(viewModel.exceptionHandler) {
                        viewModel.loadMoreJokes()
                        adapter.setNewData(viewModel.jokesFlow.value)
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
        val realJokeId : Int
        val joke = viewModel.jokesFlow.value.getOrNull(jokeId)

        if (joke != null) {
            realJokeId = joke.id
            val fragment = JokeDetailsFragment.newInstance(realJokeId)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
        else {
            showError("Попытка открыть несуществующий элемент")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
