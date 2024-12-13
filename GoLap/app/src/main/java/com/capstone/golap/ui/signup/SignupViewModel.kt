package com.capstone.golap.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.golap.repository.Repository
import com.capstone.golap.response.RegisterResponse
import com.capstone.golap.ui.Result
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: Repository) : ViewModel() {

    private val _signupResult = MutableLiveData<Result<RegisterResponse>>()
    val signupResult: LiveData<Result<RegisterResponse>> = _signupResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(email: String, password: String) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = repository.register(email, password)
            _signupResult.postValue(result)
            _isLoading.value = false
        }
    }
}





