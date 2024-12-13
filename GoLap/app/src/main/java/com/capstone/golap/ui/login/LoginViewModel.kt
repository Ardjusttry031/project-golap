package com.capstone.golap.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.golap.data.UserModel
import com.capstone.golap.repository.Repository
import com.capstone.golap.response.LoginResponse
import kotlinx.coroutines.launch
import com.capstone.golap.ui.Result

class LoginViewModel(private val repository: Repository) : ViewModel() {

    // LiveData untuk menangani hasil login
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    // Fungsi untuk melakukan login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Memanggil fungsi login dari repository
            val result = repository.login(email, password)
            // Memperbarui LiveData dengan hasil login
            _loginResult.postValue(result)
        }
    }
}

