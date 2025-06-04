package com.example.mova.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mova.R
import com.example.mova.data.source.local.DataStoreManager
import com.example.mova.databinding.ActivitySplashBinding
import com.example.mova.ui.auth.LoginActivity
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(applicationContext)

        setFadeInAnimation()
        setSystemBar()

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                val token = dataStoreManager.getAccessToken()
                val nextActivity = if (!token.isNullOrEmpty()) {
                    MainActivity::class.java
                } else {
                    LoginActivity::class.java
                }

                startActivity(Intent(this@SplashActivity, nextActivity))
                finish()
            }
        }, 1000)
    }

    private fun setFadeInAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.root.startAnimation(fadeIn)
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