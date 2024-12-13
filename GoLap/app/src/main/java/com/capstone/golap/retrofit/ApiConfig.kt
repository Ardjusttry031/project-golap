package com.capstone.golap.retrofit

import com.capstone.golap.BuildConfig
import com.capstone.golap.data.UserPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiService(userPreference: UserPreference, baseUrlType: String): ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val token = runBlocking { userPreference.getSession().firstOrNull()?.token }
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        val baseUrl = when (baseUrlType) {
            "LIST" -> "https://rest-service-dot-capstone-project-442901.et.r.appspot.com/api/"
            "REST" -> "https://model-service-dot-capstone-project-442901.et.r.appspot.com/api/"
            "AUTH" -> "https://auth-service-dot-capstone-project-442901.et.r.appspot.com/"
            else -> throw IllegalArgumentException("Unknown baseUrlType: $baseUrlType")
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

