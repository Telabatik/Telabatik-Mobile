package dev.bangkit.telabatik.view.result

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import dev.bangkit.telabatik.R
import dev.bangkit.telabatik.data.api.BatikInfo
import dev.bangkit.telabatik.databinding.ActivityBatikInfoBinding
import dev.bangkit.telabatik.view.ViewModelFactory

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

        window.statusBarColor = resources.getColor(R.color.md_theme_primary, theme)
        window.navigationBarColor = resources.getColor(R.color.md_theme_primary, theme)

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
            binding.tvBatikName.text = result.name
            binding.tvBatikSubtitle.text = getString(R.string.batik_origin, result.origin)
            binding.tvBatikDescription.text = getString(R.string.batik_info_description_placeholder)

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