package com.dicoding.telabatik.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.telabatik.R
import com.dicoding.telabatik.databinding.ActivityMainBinding
import com.dicoding.telabatik.ui.home.HomeFragment
import com.dicoding.telabatik.ui.reference.ReferenceFragment
import com.dicoding.telabatik.ui.scan.ScanFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //nav color
        window.navigationBarColor = ContextCompat.getColor(this, R.color.putih)

        // Set default fragment
        loadFragment(HomeFragment())

        // BottomNavigationView Listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_scan -> {
                    loadFragment(ScanFragment())
                    true
                }
                R.id.nav_reference -> {
                    loadFragment(ReferenceFragment())
                    true
                }else -> false
            }
        }

    }
    // Function to replace fragments
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}