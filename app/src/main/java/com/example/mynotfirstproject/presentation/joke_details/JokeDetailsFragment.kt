package com.example.mynotfirstproject.presentation.joke_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mynotfirstproject.presentation.JokeActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.databinding.JokeDetailsFragmentBinding
import com.example.mynotfirstproject.presentation.uientity.JokeUI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class JokeDetailsFragment : Fragment() {

    private var _binding: JokeDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokeDetailsViewModel by activityViewModels {
        (requireActivity() as JokeActivity).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = JokeDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jokePosition = arguments?.getInt(JOKE_POSITION_EXTRA) ?: -1
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.setJokePosition(jokePosition)
                initViewModel()
            }
        }
        
        binding.deleteJoke.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.deleteJoke()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private suspend fun initViewModel() {
        viewModel.selectedJokes.collectLatest { joke ->
            Log.d("Checking", "${viewModel.selectedJokes.value}")
            setupJokeData(joke)
        }
        viewModel.error.observe(viewLifecycleOwner) { showError(it) }
    }

    private fun setupJokeData(joke: JokeUI?) {
        if (joke != null) {
            with(binding) {
                jokeAvatar.setImageResource(joke.picture)
                jokeCategory.text = joke.category
                jokeQuestion.text = joke.setup
                jokeAnswer.text = joke.delivery
            }
        }
        else {
            showError("Something happened")
            parentFragmentManager.popBackStack()
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Unknown error", Toast.LENGTH_SHORT).show()
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun newInstance(jokePosition: Int): JokeDetailsFragment {
            val fragment = JokeDetailsFragment()
            val args = Bundle()
            args.putInt(JOKE_POSITION_EXTRA, jokePosition)
            fragment.arguments = args
            return fragment
        }
    }
}
