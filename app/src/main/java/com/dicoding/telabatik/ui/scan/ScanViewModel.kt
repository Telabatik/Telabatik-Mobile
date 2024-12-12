package com.dicoding.telabatik.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch
import java.io.File

class ScanViewModel(private val repository: MainRepository) : ViewModel() {

    fun predict(file: File) = repository.predict(file)

}