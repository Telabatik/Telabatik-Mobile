package dev.bangkit.telabatik.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(token: String = ""): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
        if (token.isNotEmpty()) {
            clientBuilder.addInterceptor(authInterceptor)
        }
        val client = clientBuilder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://telabatik-api-574359256227.asia-southeast2.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}