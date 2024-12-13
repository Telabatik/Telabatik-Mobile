package dev.bangkit.telabatik.view.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.bangkit.telabatik.data.MainRepository
import dev.bangkit.telabatik.data.pref.UserModel
import kotlinx.coroutines.launch

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