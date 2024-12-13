package com.capstone.golap.repository

import com.capstone.golap.data.UserModel
import com.capstone.golap.data.UserPreference
import com.capstone.golap.response.LoginResponse
import com.capstone.golap.response.RegisterResponse
import com.capstone.golap.retrofit.ApiConfig
import com.capstone.golap.retrofit.ApiService
import com.capstone.golap.ui.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

class Repository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun register(email: String, password: String): Result<RegisterResponse> {
        val apiService = ApiConfig.getApiService(userPreference, "AUTH")
        return try {
            val body = mapOf("email" to email, "password" to password)
            val response = apiService.postSignup(body)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Register response body is null")
            } else {
                val errorMessage = parseError(response.errorBody(), RegisterResponse::class.java)?.message
                    ?: response.message()
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        val apiService = ApiConfig.getApiService(userPreference, "AUTH")
        return try {
            val body = mapOf("email" to email, "password" to password)
            val response = apiService.postLogin(body)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Login response body is null")
            } else {
                val errorResponse = parseError(response.errorBody(), RegisterResponse::class.java)
                val errorMessage = errorResponse?.message ?: response.message()
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    private fun <T> parseError(errorBody: ResponseBody?, type: Class<T>): T? {
        return try {
            errorBody?.string()?.let { json ->
                Gson().fromJson(json, type)
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(userPreference: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference)
            }.also { instance = it }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }
}


