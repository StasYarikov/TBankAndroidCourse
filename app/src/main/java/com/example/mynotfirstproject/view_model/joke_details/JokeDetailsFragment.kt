package com.example.mynotfirstproject.view_model.joke_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mynotfirstproject.JokeActivity
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.JokeDetailsFragmentBinding
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
        initViewModel()

        val jokePosition = arguments?.getInt(JOKE_POSITION_EXTRA) ?: -1
        viewModel.setJokePosition(jokePosition)

        binding.deleteJoke.setOnClickListener {
            viewModel.deleteJoke()
            parentFragmentManager.popBackStack()
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedJoke.collectLatest { joke ->
                    setupJokeData(joke)
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { showError(it) }
    }

    private fun setupJokeData(joke: Joke?) {
        with(binding) {
            jokeAvatar.setImageResource(joke?.picture ?: R.drawable.warning)
            jokeCategory.text = joke?.category
            jokeQuestion.text = joke?.setup
            jokeAnswer.text = joke?.delivery
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
