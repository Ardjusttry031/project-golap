package com.capstone.golap.ui.dashboard

import com.capstone.golap.repository.LaptopRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.golap.response.RecomResponse
import kotlinx.coroutines.launch
import com.capstone.golap.ui.Result

class DashboardViewModel(private val repository: LaptopRepository) : ViewModel() {

    private val _recommendations = MutableLiveData<Result<List<RecomResponse>>>()
    val recommendations: LiveData<Result<List<RecomResponse>>> = _recommendations

    fun fetchRecommendations(price: Int) {
        viewModelScope.launch {
            _recommendations.value = repository.getRecommendation(price)
        }
    }
}
