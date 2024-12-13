package dev.bangkit.telabatik.data.api

import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("/auth/register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): LoginResponse2

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part image: MultipartBody.Part,
    ): PredictResponse

    @GET("predict/history")
    suspend fun getPredictionHistory(): PredictHistoryResponse
}