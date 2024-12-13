package dev.bangkit.telabatik.view.result

import androidx.lifecycle.ViewModel
import dev.bangkit.telabatik.data.MainRepository

class ResultViewModel(private val repository: MainRepository) : ViewModel() {
    fun getBatikInfo(name: String) = repository.mockGetBatikInfo(name)
}