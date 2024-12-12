package com.dicoding.telabatik.view.register

import androidx.lifecycle.ViewModel
import com.dicoding.telabatik.data.MainRepository

class RegisterViewModel(private val repository: MainRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = repository.mockRegister(name, email, password)
}