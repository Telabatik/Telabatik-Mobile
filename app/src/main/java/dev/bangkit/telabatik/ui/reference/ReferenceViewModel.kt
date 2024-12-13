package dev.bangkit.telabatik.ui.reference

import androidx.lifecycle.ViewModel
import dev.bangkit.telabatik.data.MainRepository

class ReferenceViewModel(private val repository: MainRepository) : ViewModel() {

    fun getAllBatikInfo() = repository.mockGetAllBatikInfo()

}