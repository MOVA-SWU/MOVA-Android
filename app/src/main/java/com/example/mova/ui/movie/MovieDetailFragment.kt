package com.example.mova.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.databinding.FragmentMovieDetailBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovieDetailFragment: Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        setDatePicker()
    }

    private fun setLayout() {
        binding.btnMovieDetailBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setDatePicker() {
        binding.tvMovieDetailDateField.setOnClickListener { showDatePicker() }
        binding.btnMovieDetailDate.setOnClickListener { showDatePicker() }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("")
            .setTheme(R.style.DatePicker)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val selectedDate = sdf.format(Date(selection))
            binding.tvMovieDetailDateField.text = selectedDate
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}