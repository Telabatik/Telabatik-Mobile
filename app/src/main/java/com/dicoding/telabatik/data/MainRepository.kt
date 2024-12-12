package com.dicoding.telabatik.data

import androidx.lifecycle.liveData
import com.dicoding.telabatik.data.api.ApiService
import com.dicoding.telabatik.data.api.LoginResponse
import com.dicoding.telabatik.data.api.LoginResult
import com.dicoding.telabatik.data.api.StatusResponse
import com.dicoding.telabatik.data.pref.UserModel
import com.dicoding.telabatik.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class MainRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(handleApiException(e))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(handleApiException(e))
        }
    }

    fun handleApiException(e: Exception): ResultState.Error {
        return when (e) {
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, StatusResponse::class.java)
                ResultState.Error(errorResponse.message)
            }
            else -> {
                val errorResponse = StatusResponse(error = false, message = if (e.message != null) e.message!! else "An error occured. Try again later.")
                ResultState.Error(errorResponse.message)
            }
        }
    }

    fun mockRegister(name: String, email: String, password: String) = liveData {
        val successResponse = StatusResponse(error = false, message = "")
        emit(ResultState.Success(successResponse))
    }

    fun mockLogin(email: String, password: String) = liveData {
        val successResponse = LoginResponse(loginResult = LoginResult(email, "-1", "0"), error = false, message = "")
        emit(ResultState.Success(successResponse))
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(userPreference, apiService)
            }.also { instance = it }
        fun resetInstance() {
            instance = null
        }
    }
}