package com.example.moviehub.ui.filmpage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviehub.databinding.FilmFragmentBinding

class FilmFragment : Fragment() {

    private lateinit var viewModel: FilmViewModel

    private var _binding : FilmFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FilmViewModel::class.java)

        _binding = FilmFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = "fragment Film"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
