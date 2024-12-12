package com.dicoding.telabatik.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.telabatik.data.MainRepository
import com.dicoding.telabatik.di.Injection
import com.dicoding.telabatik.view.login.LoginViewModel
import com.dicoding.telabatik.view.splash.SplashViewModel
import com.dicoding.telabatik.view.register.RegisterViewModel

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