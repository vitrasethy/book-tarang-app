package com.example.booktarang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel: ViewModel() {
    private val _registerState = MutableLiveData<ApiState<RegisterResponse>>()
    val registerState get() = _registerState

    fun register(name: String, email: String, password: String, passwordConfirmation: String) {
        var apiState = ApiState.loading<RegisterResponse>()
        _registerState.postValue(apiState)
        viewModelScope.launch {
            try {
                val response = ApiClient.get().apiService.register(email, name, password, passwordConfirmation)
                if (response.isSuccess()) {
                    apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception) {
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _registerState.postValue(apiState)
            }
        }
    }
}