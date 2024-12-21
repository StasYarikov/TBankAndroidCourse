package com.example.mynotfirstproject.presentation.add_joke

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mynotfirstproject.App
import com.example.mynotfirstproject.presentation.JokeActivity
import com.example.mynotfirstproject.databinding.FragmentAddJokeBinding
import com.example.mynotfirstproject.presentation.jokes_list.JokeListViewModel
import com.example.mynotfirstproject.presentation.viewModelFactory.JokesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddJokeFragment : Fragment() {

    private var _binding: FragmentAddJokeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: JokesViewModelFactory
    private val viewModel: AddJokeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddJokeViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

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
