package com.dicoding.telabatik.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.telabatik.R
import com.dicoding.telabatik.data.ResultState
import com.dicoding.telabatik.databinding.ActivityLoginBinding
import com.dicoding.telabatik.ui.MainActivity
import com.dicoding.telabatik.view.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.edLoginUsername.text.toString(),
                binding.edLoginPassword.text.toString()
            ).observe(this) {result ->
                if (result != null) {
                    when (result) {
//                        is ResultState.Loading -> {
//                            showLoading(true)
//                        }

                        is ResultState.Success -> {
                            showToast(result.data.message)
                            viewModel.saveSession(result.data.loginResult.userId, result.data.loginResult.token)
                            ViewModelFactory.resetInstance()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            showLoading(false)
                            finish()
                        }

//                        is ResultState.Error -> {
//                            showToast(result.error)
//                            showLoading(false)
//                        }
                    }
                }}
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}