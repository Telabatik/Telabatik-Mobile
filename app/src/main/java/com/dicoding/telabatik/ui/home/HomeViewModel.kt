package com.dicoding.telabatik.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(private val repository: MainRepository) : ViewModel() {

    fun getAllBatikInfo() = repository.mockGetAllBatikInfo()

    fun getPredictHistory() = repository.getPredictionHistory()
}