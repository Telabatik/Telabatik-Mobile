package dev.bangkit.telabatik.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bangkit.telabatik.data.MainRepository
import dev.bangkit.telabatik.di.Injection
import dev.bangkit.telabatik.ui.home.HomeViewModel
import dev.bangkit.telabatik.ui.reference.ReferenceViewModel
import dev.bangkit.telabatik.ui.scan.ScanViewModel
import dev.bangkit.telabatik.view.login.LoginViewModel
import dev.bangkit.telabatik.view.splash.SplashViewModel
import dev.bangkit.telabatik.view.register.RegisterViewModel
import dev.bangkit.telabatik.view.result.BatikInfoViewModel
import dev.bangkit.telabatik.view.result.ResultViewModel
import dev.bangkit.telabatik.view.settings.SettingsViewModel

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
            modelClass.isAssignableFrom(ReferenceViewModel::class.java) -> {
                ReferenceViewModel(repository) as T
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