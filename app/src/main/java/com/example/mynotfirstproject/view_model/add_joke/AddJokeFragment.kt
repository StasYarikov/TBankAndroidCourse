package com.example.mynotfirstproject.view_model.add_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.JokeActivity
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.FragmentAddJokeBinding
import com.example.mynotfirstproject.databinding.JokeDetailsFragmentBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.view_model.jokes_list.JokeListFragment
import com.example.mynotfirstproject.view_model.jokes_list.JokeListViewModel

class AddJokeFragment : Fragment() {

    private val viewModel: AddJokeViewModel by activityViewModels {
        (requireActivity() as JokeActivity).viewModelFactory
    }

    private var _binding: FragmentAddJokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) { showError(it) }

        binding.submitJokeButton.setOnClickListener {
            val category = binding.categoryInput.text.toString()
            val question = binding.questionInput.text.toString()
            val answer = binding.answerInput.text.toString()

            if (viewModel.addJoke(category, question, answer) == "OK")
                parentFragmentManager.popBackStack()
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Unknown error", Toast.LENGTH_SHORT).show()
    }
}
