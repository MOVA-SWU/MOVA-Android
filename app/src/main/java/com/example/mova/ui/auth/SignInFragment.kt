package com.example.mova.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setBtnColorChange()
        binding.btnSigninBack.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this)
            fragmentTransaction.commit()
        }
    }

    private fun setBtnColorChange() {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val isIdNotEmpty = binding.etSignInId.text.toString().trim().isNotEmpty()
                val isPasswordNotEmpty = binding.etSignInPassword.text.toString().trim().isNotEmpty()
                val isPasswordCheckNotEmpty = binding.etSignInPasswordCheck.text.toString().trim().isNotEmpty()
                val isNickNameNotEmpty = binding.etSignInNickname.text.toString().trim().isNotEmpty()

                if (isIdNotEmpty && isPasswordNotEmpty && isPasswordCheckNotEmpty && isNickNameNotEmpty) {
                    with(binding.btnSignIn) {
                        isEnabled = true
                        setBackgroundResource(R.drawable.background_primary_40)
                        setTextAppearance(R.style.InterMedium_White_S18)
                        setOnClickListener {
                            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.remove(this@SignInFragment)
                            fragmentTransaction.commit()
                        }
                    }
                } else {
                    with(binding.btnSignIn) {
                        isEnabled = false
                        setBackgroundResource(R.drawable.background_gray25_40)
                        setTextAppearance(R.style.InterMedium_Gray200_S18)
                    }
                }
                if (isIdNotEmpty) {
                    with(binding.btnSignInIdCheck) {
                        isEnabled = true
                        setBackgroundResource(R.drawable.background_primary_40)
                        setTextAppearance(R.style.InterMedium_White100_S16)
                    }
                } else {
                    with(binding.btnSignInIdCheck) {
                        isEnabled = false
                        setBackgroundResource(R.drawable.background_gray25_40)
                        setTextAppearance(R.style.InterMedium_Gray200_S16)
                    }
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        }
        binding.etSignInId.addTextChangedListener(watcher)
        binding.etSignInPassword.addTextChangedListener(watcher)
        binding.etSignInPasswordCheck.addTextChangedListener(watcher)
        binding.etSignInNickname.addTextChangedListener(watcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}