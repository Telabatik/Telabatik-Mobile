package com.dicoding.telabatik.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.telabatik.R
import com.dicoding.telabatik.data.ResultState
import com.dicoding.telabatik.data.api.PredictData
import com.dicoding.telabatik.data.model.ScanResult
import com.dicoding.telabatik.databinding.FragmentHomeBinding
import com.dicoding.telabatik.ui.scan.ScanViewModel
import com.dicoding.telabatik.view.ViewModelFactory
import com.dicoding.telabatik.view.result.BatikInfoActivity
import com.dicoding.telabatik.view.result.BatikInfoActivity.Companion.EXTRA_BATIK_INFO
import com.dicoding.telabatik.view.result.BatikInfoViewModel
import com.dicoding.telabatik.view.settings.SettingsActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_scan = view.findViewById<Button>(R.id.btn_scan)

        btn_scan.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scanFragment)
        }
        
        val activity = activity as AppCompatActivity
        val toolbar = binding.toolbar
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Telabatik"

//        binding.rvHistory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
//
//        val historyAdapter = HistoryAdapter()
//
//        val scanResults = mutableListOf<PredictData>()
//        for (i in 1..5) {
//            val date: Date = Calendar.getInstance().time
//            val iso8601Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date)
//            scanResults.add(PredictData(100f, iso8601Date, "https://picsum.photos/200", "$i", "Batik $i"))
//        }
//        historyAdapter.submitList(scanResults)
//
//        binding.rvHistory.adapter = historyAdapter

        viewModel.getPredictHistory().observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultState.Loading -> {
//                    showLoading(true)
                }

                is ResultState.Success -> {
                    val scanResults = result.data.data
                    binding.rvHistory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                    val adapter = HistoryAdapter()
                    adapter.submitList(scanResults)
                    binding.rvHistory.adapter = adapter
                }

                is ResultState.Error -> {
//                    showToast(result.error)
                }

            }
        })

        viewModel.getAllBatikInfo().observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultState.Loading -> {
//                    showLoading(true)
                }

                is ResultState.Success -> {
                    val batikInfo = result.data
                    binding.rvBatikinfo.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                    val adapter = BatikInfoAdapter()
                    adapter.submitList(batikInfo)
                    binding.rvBatikinfo.adapter = adapter

//                    pick random batik
                    val randomBatik = batikInfo.random()
                    Glide.with(this)
                        .load(randomBatik.image)
                        .into(binding.ivItemImg)
                    binding.tvItemTitle.text = randomBatik.name
                    binding.tvItemDescription.text = "Batik dari Indonesia"

                    binding.cardBatikOtd.setOnClickListener {
                        val intent = Intent(activity, BatikInfoActivity::class.java)
                        intent.putExtra(EXTRA_BATIK_INFO, randomBatik)
                        startActivity(intent)
                    }
                }

                is ResultState.Error -> {
//                    showToast(result.error)
//                    showLoading(false)
                }
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("HomeFragment", item.itemId.toString())
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}