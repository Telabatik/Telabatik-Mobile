package com.dicoding.telabatik.view.result

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.telabatik.R
import com.dicoding.telabatik.data.ResultState
import com.dicoding.telabatik.data.api.BatikInfo
import com.dicoding.telabatik.data.api.PredictData
import com.dicoding.telabatik.databinding.ActivityBatikInfoBinding
import com.dicoding.telabatik.databinding.ActivityResultBinding
import com.dicoding.telabatik.getRelativeTimeString
import com.dicoding.telabatik.view.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.TimeZone

class BatikInfoActivity : AppCompatActivity() {

    private val viewModel by viewModels<BatikInfoViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityBatikInfoBinding

    companion object {
        const val EXTRA_BATIK_INFO = "extra_batik_info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBatikInfoBinding.inflate(layoutInflater)
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

        val result = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_BATIK_INFO, BatikInfo::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_BATIK_INFO)
        }

        if (result != null) {
            binding.tvBatikName2.text = result.name
            binding.tvScanTime2.text = "Batik dari Indonesia"
            binding.tvResultDeskripsion2.text = "Ini adalah batik yang terdaftar di Telabatik. Sejarah dan makna akan tertera di sini."

            Glide.with(this)
                .load(result.image)
                .into(binding.ivBatik2)
        }

    }

    private fun setupAction() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}