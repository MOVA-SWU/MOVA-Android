package com.example.mova.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mova.databinding.DialogInputMovieNameBinding

class MovieNameDialogFragment: DialogFragment() {

    private var _binding: DialogInputMovieNameBinding? = null
    private val binding get() = _binding!!

    private var onMovieNameEntered: ((String) -> Unit)? = null

    fun setOnMovieNameEnteredListener(listener: (String) -> Unit) {
        this.onMovieNameEntered = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogInputMovieNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.btnDialogConfirm.setOnClickListener {
            val movieName = binding.etDialogMovieName.text.toString().trim()
            if (movieName.isNotEmpty()) {
                onMovieNameEntered?.invoke(movieName)
                dismiss()
            } else {
                binding.etDialogMovieName.error = "영화 제목을 입력하세요."
            }
        }

        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}