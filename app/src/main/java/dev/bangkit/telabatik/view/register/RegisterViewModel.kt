package dev.bangkit.telabatik.view.register

import androidx.lifecycle.ViewModel
import dev.bangkit.telabatik.data.MainRepository

class RegisterViewModel(private val repository: MainRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}