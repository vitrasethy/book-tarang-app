package com.example.booktarang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiManager
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginState = MutableStateFlow(ApiState<LoginResponse>(State.none, null))
    val loginState get() = _loginState.asStateFlow()

    fun login(email: String, password: String) {

        _loginState.update { ApiState(State.loading, null) }
        viewModelScope.launch {
            try {
                val response = ApiManager.getApiService().login(email, password)
                if(response.isSuccess()) {
                    Log.d("tarang", "Login Success")
                    _loginState.update {
                        ApiState(State.success, response.data)
                    }
                } else {
                    Log.e("tarang", "[Login] error: ${response.message}")
                    _loginState.update {
                        ApiState(State.error, null)
                    }
                }
            } catch (ex: Exception) {
                Log.e("tarang", "[Login] error: $ex")
                _loginState.update {
                    ApiState(State.error, null)
                }
            }
        }
    }
}