package com.example.mova.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mova.R
import com.example.mova.data.model.request.EmailCheckRequest
import com.example.mova.data.model.request.SignUpRequest
import com.example.mova.data.source.network.RetrofitClient
import com.example.mova.data.source.repository.AuthRepository
import com.example.mova.databinding.FragmentSignUpBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(RetrofitClient.retrofitService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setTextWatcher()

        binding.btnSigninBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .remove(this@SignUpFragment)
                .commit()
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.etSignInId.text.toString()
            val password = binding.etSignInPassword.text.toString()
            val passwordCheck = binding.etSignInPasswordCheck.text.toString()
            val nickname = binding.etSignInNickname.text.toString()

            if (email.isBlank() || password.isBlank() || passwordCheck.isBlank() || nickname.isBlank()) {
                Toast.makeText(context, "모든 항목을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (password != passwordCheck) {
                    Toast.makeText(context, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    val signUpRequest = SignUpRequest(email, password, nickname)
                    viewModel.signUp(signUpRequest)
                    observeSignUpResult()
                }
            }
        }

        binding.btnSignInIdCheck.setOnClickListener {
            val email = binding.etSignInId.text.toString().trim()

            if (email.isBlank()) {
                Toast.makeText(context, "이메일을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                viewModel.emailCheck(EmailCheckRequest(email))
                observeEmailCheckResult()
            }
        }
    }

    private fun setTextWatcher() {
        val idTextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val isIdNotEmpty = binding.etSignInId.text.toString().trim().isNotEmpty()
                binding.btnSignInIdCheck.apply {
                    setBackgroundResource(if (isIdNotEmpty) R.drawable.background_primary_40 else R.drawable.background_gray25_40)
                    setTextAppearance(if (isIdNotEmpty) R.style.InterMedium_White100_S16 else R.style.InterMedium_Gray200_S16)
                    isEnabled = isIdNotEmpty
                }
                updateSignInButtonState()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }

        val commonTextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                updateSignInButtonState()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }

        binding.etSignInId.addTextChangedListener(idTextWatcher)
        binding.etSignInPassword.addTextChangedListener(commonTextWatcher)
        binding.etSignInPasswordCheck.addTextChangedListener(commonTextWatcher)
        binding.etSignInNickname.addTextChangedListener(commonTextWatcher)
    }

    private fun updateSignInButtonState() {
        val isIdNotEmpty = binding.etSignInId.text.toString().trim().isNotEmpty()
        val isPasswordNotEmpty = binding.etSignInPassword.text.toString().trim().isNotEmpty()
        val isPasswordCheckNotEmpty = binding.etSignInPasswordCheck.text.toString().trim().isNotEmpty()
        val isNickNameNotEmpty = binding.etSignInNickname.text.toString().trim().isNotEmpty()

        val isAllFilled = isIdNotEmpty && isPasswordNotEmpty && isPasswordCheckNotEmpty && isNickNameNotEmpty

        binding.btnSignIn.apply {
            setBackgroundResource(if (isAllFilled) R.drawable.background_primary_40 else R.drawable.background_gray25_40)
            setTextAppearance(if (isAllFilled) R.style.InterMedium_White_S18 else R.style.InterMedium_Gray200_S18)
        }
    }

    private fun observeSignUpResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signUpResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
                            parentFragmentManager.beginTransaction()
                                .remove(this@SignUpFragment)
                                .commit()
                        } else {
                            Toast.makeText(context, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                        viewModel.clearSignUp()
                    }
                }
        }
    }

    private fun observeEmailCheckResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.emailCheckResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            Toast.makeText(context, "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show()
                            binding.btnSignInIdCheck.apply {
                                isEnabled = false
                                setBackgroundResource(R.drawable.background_gray25_40)
                                setTextAppearance(R.style.InterMedium_Gray200_S18)
                            }
                        } else {
                            Toast.makeText(context, "이미 사용 중인 아이디입니다", Toast.LENGTH_SHORT).show()
                        }
                        viewModel.clearEmailCheck()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}