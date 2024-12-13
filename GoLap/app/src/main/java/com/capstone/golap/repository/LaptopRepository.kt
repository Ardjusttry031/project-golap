package com.capstone.golap.repository

import com.capstone.golap.data.UserPreference
import com.capstone.golap.response.DetailResponse
import com.capstone.golap.response.RecomResponse
import com.capstone.golap.retrofit.ApiConfig
import com.capstone.golap.retrofit.ApiService
import com.capstone.golap.ui.Result

class LaptopRepository(private val apiService: ApiService, private val userPreference: UserPreference) {

    suspend fun getRecommendation(price: Int): Result<List<RecomResponse>> {
        val apiServiceForREST = ApiConfig.getApiService(userPreference, "REST")
        return try {
            val response = apiServiceForREST.getRecommendation(price)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("Failed to fetch recommendations: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun fetchLaptopList(): Result<List<DetailResponse>> {
        val apiServiceForLIST = ApiConfig.getApiService(userPreference, "LIST")
        return try {
            val response = apiServiceForLIST.getList()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Empty Response Body")
            } else {
                Result.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun fetchLaptopDetail(laptopId: String): Result<DetailResponse> {
        return try {
            // Mengambil detail laptop berdasarkan ID
            val response = apiService.getLaptopDetail(laptopId)

            if (response.isSuccessful) {
                // Mengembalikan DetailResponse langsung
                response.body()?.let {
                    Result.Success(it)  // Return the full DetailResponse if successful
                } ?: Result.Error("Empty response body")
            } else {
                Result.Error("Failed to fetch data")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: LaptopRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): LaptopRepository =
            instance ?: synchronized(this) {
                instance ?: LaptopRepository(apiService, userPreference).also { instance = it }
            }
    }
}
