package com.capstone.golap.ui

import com.capstone.golap.repository.LaptopRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.golap.di.Injection
import com.capstone.golap.repository.Repository
import com.capstone.golap.ui.dashboard.DashboardViewModel
import com.capstone.golap.ui.detail.DetailViewModel
import com.capstone.golap.ui.home.HomeViewModel
import com.capstone.golap.ui.login.LoginViewModel
import com.capstone.golap.ui.signup.SignupViewModel

class ViewModelFactory private constructor(
    private val repository: Repository?,
    private val laptopRepository: LaptopRepository?
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository!!) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository!!) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(laptopRepository!!) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(laptopRepository!!) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(laptopRepository!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        // Memberikan ViewModelFactory berdasarkan kebutuhan repository
        fun getInstance(context: Context, baseUrlType: String): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    repository = when (baseUrlType) {
                        "AUTH" -> Injection.provideRepository(context) // AUTH repository
                        else -> null
                    },
                    laptopRepository = when (baseUrlType) {
                        "REST" -> Injection.provideLaptopRepositoryForRecommendation(context) // REST repository
                        "LIST" -> Injection.provideLaptopRepositoryForList(context) // LIST repository
                        else -> null
                    }
                ).also { instance = it }
            }
        }
    }
}
