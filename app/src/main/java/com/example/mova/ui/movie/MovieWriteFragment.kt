package com.example.mova.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.databinding.FragmentMovieWriteBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovieWriteFragment: Fragment() {

    private var _binding: FragmentMovieWriteBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        binding.btnMovieWriteBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setTodayDate()
        setDatePicker()
        setEditTextScroll()
        setBtnColorChange()
    }

    private fun setBtnColorChange() {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val isNameNotEmpty = binding.etMovieWriteNameField.text.toString().trim().isNotEmpty()
                val isContentNotEmpty = binding.etMovieWriteContent.text.toString().trim().isNotEmpty()

                if (isNameNotEmpty && isContentNotEmpty) {
                    binding.btnMovieWriteAdd.isEnabled = true
                    binding.btnMovieWriteAdd.setBackgroundResource(R.drawable.background_primary_40)
                    binding.btnMovieWriteAdd.setTextAppearance(R.style.InterMedium_White_S18)
                } else {
                    binding.btnMovieWriteAdd.isEnabled = false
                    binding.btnMovieWriteAdd.setBackgroundResource(R.drawable.background_gray25_40)
                    binding.btnMovieWriteAdd.setTextAppearance(R.style.InterMedium_Gray200_S18)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        }
        binding.etMovieWriteNameField.addTextChangedListener(watcher)
        binding.etMovieWriteContent.addTextChangedListener(watcher)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setEditTextScroll() {
        binding.etMovieWriteContent.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    private fun setTodayDate() {
        val currentDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date())
        binding.tvMovieWriteDateField.text = currentDate
    }

    private fun setDatePicker() {
        binding.tvMovieWriteDateField.setOnClickListener { showDatePicker() }
        binding.btnMovieWriteDate.setOnClickListener { showDatePicker() }
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
            binding.tvMovieWriteDateField.text = selectedDate
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}