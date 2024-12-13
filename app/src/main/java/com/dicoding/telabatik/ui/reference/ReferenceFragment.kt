package com.dicoding.telabatik.ui.reference

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.telabatik.databinding.FragmentReferenceBinding


class ReferenceFragment : Fragment() {

    // Mendeklarasikan binding sebagai variabel nullable
    private var _binding: FragmentReferenceBinding? = null
    // Variabel binding yang akan diakses di seluruh fragment
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginflate layout menggunakan View Binding
        _binding = FragmentReferenceBinding.inflate(inflater, container, false)


        // Kembalikan root view untuk fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
