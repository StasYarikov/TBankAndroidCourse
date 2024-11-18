package com.example.mynotfirstproject.view_model.add_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.FragmentAddJokeBinding
import com.example.mynotfirstproject.databinding.JokeDetailsFragmentBinding
import com.example.mynotfirstproject.view_model.JokesViewModelFactory
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.view_model.jokes_list.JokeListFragment
import com.example.mynotfirstproject.view_model.jokes_list.JokeListViewModel

class AddJokeFragment(
    private var viewModel: JokeListViewModel
) : Fragment() {

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

        binding.submitJokeButton.setOnClickListener {
            val category = binding.categoryInput.text.toString()
            val question = binding.questionInput.text.toString()
            val answer = binding.answerInput.text.toString()

            if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
                viewModel.addJoke(Joke(category = category, question = question, answer = answer, picture = JokeGenerator.generateRandomPicture()))
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
