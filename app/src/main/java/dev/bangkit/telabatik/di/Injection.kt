package dev.bangkit.telabatik.di

import android.content.Context
import dev.bangkit.telabatik.data.MainRepository
import dev.bangkit.telabatik.data.pref.UserPreference
import dev.bangkit.telabatik.data.pref.dataStore
import dev.bangkit.telabatik.data.api.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): MainRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return MainRepository.getInstance(pref, apiService)
    }

    fun resetRepository() {
        MainRepository.resetInstance()
    }
}