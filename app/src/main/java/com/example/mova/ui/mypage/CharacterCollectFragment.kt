package com.example.mova.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.data.model.Character
import com.example.mova.databinding.FragmentCharacterCollectBinding

class CharacterCollectFragment : Fragment() {

    private var _binding: FragmentCharacterCollectBinding? = null
    private val binding get() = _binding!!

    private val characterCollectAdapter = CharacterCollectAdapter()

    val dummyCharacter = listOf(
        Character(1, ""),
        Character(2, ""),
        Character(3, ""),
        Character(4, ""),
        Character(5, "")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterCollectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()

    }

    private fun setLayout() {
        binding.btnCharacterCollectBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.rvCharacterCollect.adapter = characterCollectAdapter
        characterCollectAdapter.submitWithPlaceholders(dummyCharacter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}