package com.example.mynotfirstproject.view_model.joke_details

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
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.databinding.JokeDetailsFragmentBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory

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
    }

    private fun initViewModel() {
        viewModel.selectedJoke.observe(viewLifecycleOwner) { joke -> setupJokeData(joke) }
        viewModel.error.observe(viewLifecycleOwner) { showError(it) }
    }

    private fun setupJokeData(joke: Joke) {
        with(binding) {
            jokeAvatar.setImageResource(joke.picture ?: R.drawable.warning)
            jokeCategory.text = joke.category
            jokeQuestion.text = joke.question
            jokeAnswer.text = joke.answer
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
