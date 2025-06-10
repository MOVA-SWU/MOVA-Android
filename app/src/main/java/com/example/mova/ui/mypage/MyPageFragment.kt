package com.example.mova.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.data.source.local.DataStoreManager
import com.example.mova.data.source.remote.network.RetrofitClient
import com.example.mova.data.source.remote.repository.MyPageRepository
import com.example.mova.databinding.FragmentMypageBinding
import com.example.mova.ui.auth.LoginActivity
import com.example.mova.ui.extensions.load
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyPageViewModel by activityViewModels {
        MyPageViewModelFactory(MyPageRepository(RetrofitClient.retrofitService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setViewModel()
        binding.viewMypageCharacter.setOnClickListener {
            findNavController().navigate(R.id.action_mypage_to_character_collect)
        }
        binding.viewMypageRight.setOnClickListener {
            findNavController().navigate(R.id.action_mypage_to_mission_donation)
        }

        binding.btnMypageNicknameEdit.setOnClickListener {
            showDialog()
        }

        binding.tvMypageLogout.setOnClickListener {
            viewModel.logout()
            logout()
        }
    }

    private fun showDialog() {
        val dialogFragment = NickNameDialogFragment()
        dialogFragment.show(parentFragmentManager, "NicknameDialog")
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.logoutResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            val dataStoreManager = DataStoreManager(requireContext())
                            dataStoreManager.clearTokens()

                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(context, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                        }
                        viewModel.clearLogout()
                    }
                }
        }
    }

    private fun setViewModel() {
        viewModel.loadProfile()
        viewModel.loadPointSum()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.profileResponse.collectLatest { result ->
                        result?.let {
                            if (it.isSuccess) {
                                binding.ivMypageProfile.load(it.getOrNull()?.profileImage)
                                binding.tvMypageNickname.text = it.getOrNull()?.nickname
                            } else {
                                Toast.makeText(context, "회원정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                            }
                            viewModel.clearProfile()
                        }
                    }
                }
                launch {
                    viewModel.pointSumResponse.collectLatest { result ->
                        result?.let {
                            if (it.isSuccess) {
                                binding.tvMypagePointField.text = "${it.getOrNull()?.totalPoints} P"
                            } else {
                                Toast.makeText(context, "회원정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
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