package com.example.mova.ui.missiondonation.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.data.model.Movie
import com.example.mova.databinding.FragmentCompanyDetailBinding

class CompanyDetailFragment : Fragment() {

    private var _binding: FragmentCompanyDetailBinding? = null
    private val binding get() = _binding!!

    private val companyMovieAdapter = CompanyMovieAdapter()

    val dummyMovies = listOf(
        Movie(1, ""),
        Movie(2, ""),
        Movie(3, ""),
        Movie(4, ""),
        Movie(5, "")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        binding.btnCompanyDetailDonate.setOnClickListener {
            findNavController().navigateUp()
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.rvCompanyDetailMovie.adapter = companyMovieAdapter
        companyMovieAdapter.submitList(dummyMovies)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}