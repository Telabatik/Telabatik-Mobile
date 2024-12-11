package com.dicoding.telabatik.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.telabatik.R

class HomeFragment : Fragment() {

    // Variabel RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflasi layout fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi RecyclerView setelah layout diinflasi
        recyclerView = rootView.findViewById(R.id.rv_history)

        // Inisialisasi LinearLayoutManager dengan orientasi horizontal
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Set layoutManager untuk RecyclerView
        recyclerView.layoutManager = layoutManager

        // Anda bisa menambahkan adapter di sini jika perlu

        return rootView
    }
}
