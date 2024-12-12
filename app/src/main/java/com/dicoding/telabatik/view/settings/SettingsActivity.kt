package com.dicoding.telabatik.view.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.telabatik.R
import com.dicoding.telabatik.databinding.ActivitySettingsBinding
import com.dicoding.telabatik.view.ViewModelFactory
import com.dicoding.telabatik.view.welcome.WelcomeActivity

class SettingsActivity : AppCompatActivity() {
    private val viewModel by viewModels<SettingsViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySettingsBinding

    companion object {
        const val EXTRA_STORY = "extra_story"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupAction()
//        playAnimation()
    }

    private fun setupView() {
        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val result = if (Build.VERSION.SDK_INT >= 33) {
//            intent.getParcelableExtra(EXTRA_STORY, ScanResult::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getParcelableExtra(EXTRA_STORY)
//        }
//
//        if (result != null) {
//            binding.tvBatikName2.text = result.name
//            binding.tvScanTime2.text = getRelativeTimeString(result.dateCreated)
//        }

    }
    private fun setupAction() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnSettingsLogout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin or user.token.isNullOrBlank()) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}