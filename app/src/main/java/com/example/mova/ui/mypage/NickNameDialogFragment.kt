package com.example.mova.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mova.data.source.remote.network.RetrofitClient
import com.example.mova.data.source.remote.repository.MyPageRepository
import com.example.mova.databinding.DialogNicknameBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NickNameDialogFragment: DialogFragment() {

    private var _binding: DialogNicknameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyPageViewModel by activityViewModels {
        MyPageViewModelFactory(MyPageRepository(RetrofitClient.retrofitService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNicknameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        setLayout()
    }

    private fun setLayout() {
        binding.btnDialogConfirm.setOnClickListener {
            val nickname = binding.etDialogNickname.text.toString().trim()
            if (nickname.isNotEmpty()) {
                viewModel.changeNickname(nickname)
                observeNicknameChange()
            } else {
                binding.etDialogNickname.error = "변경할 닉네임을 입력하세요."
            }
        }

        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun observeNicknameChange() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nicknameResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            dismiss()
                            Toast.makeText(context, "닉네임이 변경 되었습니다", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "닉네임 변경 실패", Toast.LENGTH_SHORT).show()
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