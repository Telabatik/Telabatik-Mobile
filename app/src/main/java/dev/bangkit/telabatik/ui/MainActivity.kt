package dev.bangkit.telabatik.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dev.bangkit.telabatik.R
import dev.bangkit.telabatik.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        // Mengambil NavController dari NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_view) as NavHostFragment
        val navController = navHostFragment.navController

        // Mengambil BottomNavigationView dan menghubungkannya dengan NavController
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        window.statusBarColor = resources.getColor(R.color.md_theme_primary, theme)
        window.navigationBarColor = resources.getColor(R.color.md_theme_primary, theme)

        // Konfigurasi untuk AppBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_scan, R.id.nav_reference
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}


