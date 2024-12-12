package com.dicoding.telabatik.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.telabatik.R
import com.dicoding.telabatik.databinding.ActivitySplashBinding
import com.dicoding.telabatik.databinding.ActivityWelcomeBinding
import com.dicoding.telabatik.view.login.LoginActivity
import com.dicoding.telabatik.view.register.RegisterActivity
import com.dicoding.telabatik.view.splash.SplashActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnWelcomeLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
        }

        binding.btnWelcomeRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
//            finish()
        }
    }
}