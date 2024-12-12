package com.dicoding.telabatik.view.result

import android.content.Intent
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
import com.dicoding.telabatik.data.api.PredictData
import com.dicoding.telabatik.databinding.ActivityResultBinding
import com.dicoding.telabatik.getRelativeTimeString
import com.dicoding.telabatik.view.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.TimeZone

class ResultActivity : AppCompatActivity() {

    private val viewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityResultBinding

    companion object {
        const val EXTRA_PREDICT_DATA = "extra_predict_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
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
            intent.getParcelableExtra(EXTRA_PREDICT_DATA, PredictData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PREDICT_DATA)
        }

        if (result != null) {
            binding.tvBatikName2.text = result.label
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = dateFormat.parse(result.predictedAt)

            binding.tvScanTime2.text = getRelativeTimeString(date)
            binding.tvResultDeskripsion2.text = "Confidence: ${result.confidenceScore}"

            viewModel.getBatikInfo(result.label).observe(this) { result ->
                when (result) {
                    is ResultState.Success -> {
                        binding.hriResultLearnMore.setTitleText(result.data.name)
                        binding.hriResultLearnMore.setDescriptionText("Batik dari Indonesia")
//                        binding.hriResultLearnMore.imageView
                        Glide.with(this)
                            .load(result.data.image)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                                    return false
                                }
                                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                                    return false
                                }
                            })
                            .into(binding.hriResultLearnMore.imageView)
                        binding.hriResultLearnMore.setOnClickListener() {
                            val intent = Intent(this, BatikInfoActivity::class.java)
                            intent.putExtra(BatikInfoActivity.EXTRA_BATIK_INFO, result.data)
                            startActivity(intent)
                        }
                    }

                    is ResultState.Loading -> {
//                        showLoading(true)
                    }

                    is ResultState.Error -> {
//                        showLoading(true)
                    }
                }
            }

            Glide.with(this)
                .load(result.imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
//                        binding.progressBarItem.visibility = View.GONE
//                        binding.ivItemVertImg.setImageResource(android.R.drawable)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
//                        binding.progressBarItem.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.ivBatik2)
        }

    }

    private fun setupAction() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}