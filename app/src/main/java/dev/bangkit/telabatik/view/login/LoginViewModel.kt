package dev.bangkit.telabatik.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bangkit.telabatik.data.MainRepository
import dev.bangkit.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: MainRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(email: String, username: String, token: String) {
        viewModelScope.launch {
            val user = UserModel(email, username, token, true)
            runBlocking { repository.saveSession(user) }
        }
    }
}