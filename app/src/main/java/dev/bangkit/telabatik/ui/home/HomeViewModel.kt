package dev.bangkit.telabatik.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.bangkit.telabatik.data.MainRepository
import dev.bangkit.telabatik.data.pref.UserModel

class HomeViewModel(private val repository: MainRepository) : ViewModel() {

    fun getAllBatikInfo() = repository.mockGetAllBatikInfo()

    fun getPredictHistory() = repository.getPredictionHistory()

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}