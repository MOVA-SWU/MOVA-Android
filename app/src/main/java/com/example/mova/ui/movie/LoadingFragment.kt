package com.example.mova.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {

    private var _binding : FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        binding.btnLoadingBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivLoading.setOnClickListener {
            findNavController().navigate(R.id.action_loading_to_ai)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}