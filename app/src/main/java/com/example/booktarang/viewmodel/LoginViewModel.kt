package com.example.booktarang.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.api.ApiManager
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {

    private val _loginState = MutableLiveData<ApiState<LoginResponse>>()
    val loginState get() = _loginState

    fun login(email: String, password: String) {
        var apiState = ApiState.loading<LoginResponse>()
        _loginState.postValue(apiState)
        viewModelScope.launch {
            try {
                val response = ApiClient.get().apiService.login(email, password)
                if (response.isSuccess()) {
                    apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception) {
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _loginState.postValue(apiState)
            }
        }
    }
}