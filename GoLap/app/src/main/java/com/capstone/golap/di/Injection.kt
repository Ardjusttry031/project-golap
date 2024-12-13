package com.capstone.golap.di

import com.capstone.golap.repository.LaptopRepository
import android.content.Context
import com.capstone.golap.data.UserPreference
import com.capstone.golap.data.dataStore
import com.capstone.golap.repository.Repository
import com.capstone.golap.retrofit.ApiConfig

object Injection {

    // Menyediakan Repository untuk kebutuhan umum
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, "AUTH") // Base URL untuk otentikasi
        return Repository.getInstance(pref)
    }

    // Menyediakan LaptopRepository untuk pengambilan data rekomendasi laptop
    fun provideLaptopRepositoryForRecommendation(context: Context): LaptopRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, "REST") // Base URL untuk rekomendasi
        return LaptopRepository(apiService, pref)
    }

    // Menyediakan LaptopRepository untuk pengambilan daftar laptop
    fun provideLaptopRepositoryForList(context: Context): LaptopRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, "LIST") // Base URL untuk daftar laptop
        return LaptopRepository(apiService, pref)
    }
}

