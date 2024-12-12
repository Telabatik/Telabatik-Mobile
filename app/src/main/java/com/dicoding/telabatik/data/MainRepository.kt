package com.dicoding.telabatik.data

import androidx.lifecycle.liveData
import com.dicoding.telabatik.data.api.ApiService
import com.dicoding.telabatik.data.api.BatikInfo
import com.dicoding.telabatik.data.api.LoginResponse
import com.dicoding.telabatik.data.api.LoginResult
import com.dicoding.telabatik.data.api.PredictResponse
import com.dicoding.telabatik.data.api.StatusResponse
import com.dicoding.telabatik.data.pref.UserModel
import com.dicoding.telabatik.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

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

    fun predict(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.predict(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        } catch (e: Exception) {
            emit(ResultState.Error(if (e.message != null) e.message!! else "An error occured. Try again later."))
        }

    }

    fun getPredictionHistory() = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getPredictionHistory()
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(handleApiException(e))
        }
    }

    val mockBatikInfoList = listOf(
        BatikInfo(name = "Batik Buketan", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Buketan/buketan_278.jpg"),
        BatikInfo(name = "Batik Cendrawasih", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Cendrawasih/0020.jpg"),
        BatikInfo(name = "Batik Ceplok", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Ceplok/40.jpg"),
        BatikInfo(name = "Batik Corak Insang", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Corak%20Insang/Corakinsang%20(19).jpg"),
        BatikInfo(name = "Batik Dayak", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Dayak/23.jpg"),
        BatikInfo(name = "Batik Gunungan", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Gunungan/gunungan%20(22)b.jpg"),
        BatikInfo(name = "Batik Ikat Celup", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Ikat%20Celup/27.jpg"),
        BatikInfo(name = "Batik Kawung", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Ikat%20Celup/27.jpg"),
        BatikInfo(name = "Batik Lereng", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Lereng/48.jpg"),
        BatikInfo(name = "Batik Megamendung", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Megamendung/12.jpg"),
        BatikInfo(name = "Batik Nitik", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Nitik/10.jpg"),
        BatikInfo(name = "Batik Parang", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Parang/001.jpg"),
        BatikInfo(name = "Batik Prada", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Prada/19_jpg.rf.eac3aeb214e123249b4ac00d8cf4ddc8.jpg"),
        BatikInfo(name = "Batik Sekar", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Sekar/54.jpg"),
        BatikInfo(name = "Batik Sidoluhur", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Sidoluhur/Batik%20Si%20(12).jpg"),
        BatikInfo(name = "Batik Truntum", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Truntum/truntum.172.jpg"),
        BatikInfo(name = "Batik Tumpal", image = "https://raw.githubusercontent.com/Telabatik/Machine-Learning/refs/heads/main/Dataset/train/Batik%20Tumpal/9_jpg.rf.f8bdcb7f7625859624a38220bed9a02e.jpg"),
    )

    fun mockGetAllBatikInfo() = liveData {
        emit(ResultState.Loading)
        emit(ResultState.Success(mockBatikInfoList))
    }

    fun mockGetBatikInfo(name: String) = liveData {
        val batikInfo = mockBatikInfoList.find { it.name == name }
        if (batikInfo != null) {
            emit(ResultState.Success(batikInfo))
        } else {
            emit(ResultState.Error("Batik not found"))
        }
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