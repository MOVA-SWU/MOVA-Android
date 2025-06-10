package com.example.mova.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.databinding.FragmentCharacterCollectBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterCollectFragment : Fragment() {

    private var _binding: FragmentCharacterCollectBinding? = null
    private val binding get() = _binding!!

    private val characterCollectAdapter = CharacterCollectAdapter()

    private val viewModel: MyPageViewModel by viewModels()

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
        binding.btnCharacterCollectAdd.setOnClickListener {
            findNavController().navigate(R.id.action_character_collect_to_mission_donation)
        }
        viewModel.loadCharacterCollect()
        setAdapter()
    }

    private fun setAdapter() {
        binding.rvCharacterCollect.adapter = characterCollectAdapter
        setViewModel()
    }

    private fun setViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characterCollectResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            val response = it.getOrNull()
                            if (response != null) {
                                binding.tvCharacterCollectField.text = "${response.imageCount}개"
                                characterCollectAdapter.submitWithPlaceholders(response.imageUrls)
                            }
                        } else {
                            Toast.makeText(context, "캐릭터 수집 불러오기 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}