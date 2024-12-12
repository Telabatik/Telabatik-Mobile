package com.dicoding.telabatik.view.register

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
import com.dicoding.telabatik.databinding.ActivityRegisterBinding
import com.dicoding.telabatik.view.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

//        binding.passwordEditText.doOnTextChanged() { text, _, _, _ ->
//            if (text.toString().length < 8) {
//                binding.passwordEditTextLayout.error = getString(R.string.error_password_too_short)
//            } else {
//                binding.passwordEditTextLayout.error = null
//            }
//        }
//
//        binding.emailEditText.doOnTextChanged() { text, _, _, _ ->
//            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {
//                binding.emailEditTextLayout.error = getString(R.string.error_invalid_email)
//            } else {
//                binding.emailEditTextLayout.error = null
//            }
//        }
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.edRegisterUsername.text.toString(),
                binding.edRegisterEmail.text.toString(),
                binding.edRegisterPassword.text.toString()
            ).observe(this) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showToast(result.data.message)
                        showLoading(false)
                        finish()
                    }

                    is ResultState.Error -> {
                        showToast(result.error)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbSignup.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}