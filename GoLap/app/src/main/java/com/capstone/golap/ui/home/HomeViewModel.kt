package com.capstone.golap.ui.home

import com.capstone.golap.repository.LaptopRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.golap.response.DetailResponse
import com.capstone.golap.ui.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: LaptopRepository) : ViewModel() {

    private val _imageLinks = MutableLiveData<Result<List<String>>>()
    val imageLinks: LiveData<Result<List<String>>> get() = _imageLinks

    private val _laptops = MutableLiveData<Result<List<DetailResponse>>>()
    val laptops: LiveData<Result<List<DetailResponse>>> get() = _laptops

    fun fetchImageLinks() {
        viewModelScope.launch {
            _imageLinks.value = Result.Loading // Show loading state

            try {
                val result = repository.fetchLaptopList() // Fetch the laptop list
                if (result is Result.Success) {
                    val imageUrls = result.data?.map { it.imageLink } ?: emptyList()
                    _imageLinks.value = Result.Success(imageUrls) // Update with image URLs
                } else {
                    _imageLinks.value = Result.Error("Failed to load images") // Handle errors
                }
            } catch (e: Exception) {
                _imageLinks.value = Result.Error("Error fetching image links: ${e.message}") // Handle exception
            }
        }
    }

    fun fetchLaptops() {
        viewModelScope.launch {
            _laptops.value = Result.Loading // Show loading state

            try {
                val result = repository.fetchLaptopList() // Fetch the full list of laptops
                _laptops.value = result // Pass the result (either Success or Error)
            } catch (e: Exception) {
                _laptops.value = Result.Error("Error fetching laptop list: ${e.message}") // Handle exception
            }
        }
    }
}

