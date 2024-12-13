package com.capstone.golap.ui.detail

import com.capstone.golap.repository.LaptopRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.golap.ui.Result
import com.capstone.golap.response.DetailResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: LaptopRepository) : ViewModel() {

    private val _laptops = MutableLiveData<Result<List<DetailResponse>>>()
    val laptops: LiveData<Result<List<DetailResponse>>> get() = _laptops

    fun fetchLaptops() {
        _laptops.value = Result.Loading
        viewModelScope.launch {
            _laptops.value = repository.fetchLaptopList()
        }
    }
}
