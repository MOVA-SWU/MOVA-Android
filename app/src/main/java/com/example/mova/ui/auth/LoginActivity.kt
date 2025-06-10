package com.example.mova.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mova.R
import com.example.mova.data.model.request.LogInRequest
import com.example.mova.data.source.local.DataStoreManager
import com.example.mova.databinding.ActivityLoginBinding
import com.example.mova.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSystemBar()
        setLayout()

        dataStoreManager = DataStoreManager(applicationContext)
    }

    private fun setLayout() {
        binding.tvSignIn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(binding.main.id, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginId.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "모든 항목을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val loginRequest = LogInRequest(email, password)
                viewModel.logIn(loginRequest)
                observeLogInResult()
            }
        }
    }

    private fun observeLogInResult() {
        lifecycleScope.launch {
            viewModel.loginResponse
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            val response = it.getOrNull()
                            response?.let {
                                lifecycleScope.launch {
                                    dataStoreManager.saveAccessToken(it.token.accessToken)
                                    dataStoreManager.saveRefreshToken(it.token.refreshToken)
                                }
                            }
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                        viewModel.clearLogIn()
                    }
                }
        }
    }

    private fun setSystemBar() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}