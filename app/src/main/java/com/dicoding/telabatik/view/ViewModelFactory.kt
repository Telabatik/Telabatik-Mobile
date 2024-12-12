package com.dicoding.telabatik.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.di.Injection
import com.dicoding.telabatik.ui.home.HomeViewModel
import com.dicoding.telabatik.ui.scan.ScanViewModel
import com.dicoding.telabatik.view.login.LoginViewModel
import com.dicoding.telabatik.view.splash.SplashViewModel
import com.dicoding.telabatik.view.register.RegisterViewModel
import com.dicoding.telabatik.view.result.BatikInfoViewModel
import com.dicoding.telabatik.view.result.ResultViewModel
import com.dicoding.telabatik.view.settings.SettingsViewModel

class ViewModelFactory(private val repository: MainRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BatikInfoViewModel::class.java) -> {
                BatikInfoViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
        @JvmStatic
        fun resetInstance() {
            if (INSTANCE != null) {
                synchronized(ViewModelFactory::class.java) {
                    Injection.resetRepository()
                    INSTANCE = null
                }
            }
        }
    }
}