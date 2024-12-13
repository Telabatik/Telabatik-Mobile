package dev.bangkit.telabatik.ui.scan

import androidx.lifecycle.ViewModel
import dev.bangkit.telabatik.data.MainRepository
import java.io.File

class ScanViewModel(private val repository: MainRepository) : ViewModel() {

    fun predict(file: File) = repository.predict(file)

}