package com.dicoding.telabatik.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: MainRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}