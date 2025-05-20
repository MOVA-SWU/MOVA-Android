package com.example.mova.ui.movie.ai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.databinding.FragmentAiBinding

class AiFragment : Fragment() {
    private var _binding : FragmentAiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        binding.btnAiBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAiMissionStart.setOnClickListener {
            findNavController().navigate(R.id.action_ai_to_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}