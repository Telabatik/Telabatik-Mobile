package com.dicoding.telabatik.view.result

import androidx.lifecycle.ViewModel
import com.dicoding.telabatik.data.MainRepository

class ResultViewModel(private val repository: MainRepository) : ViewModel() {
    fun getBatikInfo(name: String) = repository.mockGetBatikInfo(name)
}