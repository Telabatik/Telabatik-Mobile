package com.dicoding.telabatik.view.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsViewModel(private val repository: MainRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}