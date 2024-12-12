package com.dicoding.telabatik.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.dicoding.telabatik.R
import com.dicoding.telabatik.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_scan = view.findViewById<Button>(R.id.btn_scan)

        // Menambahkan aksi klik pada tombol
        btn_scan.setOnClickListener {
            // Pindah ke ScanFragment menggunakan Navigation Component
            findNavController().navigate(R.id.action_homeFragment_to_scanFragment)
        }
        
        // Toolbar sebagai ActionBar di Fragment
        val activity = activity as AppCompatActivity
        val toolbar = binding.toolbar
        activity.setSupportActionBar(toolbar)

        // Menampilkan tombol navigasi (icon back) jika perlu
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Telabatik"


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Menangani aksi tombol back (atau membuka profil)
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}