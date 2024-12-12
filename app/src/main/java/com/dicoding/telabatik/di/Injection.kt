package com.dicoding.telabatik.di

import android.content.Context
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.data.pref.UserPreference
import com.dicoding.telabatik.data.pref.dataStore
import com.dicoding.telabatik.data.api.ApiConfig
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