package com.example.mynotfirstproject.view_model.add_joke

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
import com.example.mynotfirstproject.databinding.FragmentAddJokeBinding
import kotlinx.coroutines.launch

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

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    if (viewModel.addJoke(category, question, answer) == "Success")
                        parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Unknown error", Toast.LENGTH_SHORT).show()
    }
}
