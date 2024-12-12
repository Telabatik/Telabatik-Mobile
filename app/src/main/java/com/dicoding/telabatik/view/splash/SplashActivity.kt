package com.dicoding.telabatik.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.telabatik.R
import com.dicoding.telabatik.databinding.ActivitySplashBinding
import com.dicoding.telabatik.ui.MainActivity
import com.dicoding.telabatik.view.ViewModelFactory
import com.dicoding.telabatik.view.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin or user.token.isNullOrBlank()) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }
}