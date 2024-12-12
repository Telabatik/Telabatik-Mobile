package com.dicoding.telabatik.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: MainRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.mockLogin(email, password)

    fun saveSession(email: String, token: String) {
        viewModelScope.launch {
            val user = UserModel(email, token, true)
            runBlocking { repository.saveSession(user) }
        }
    }
}