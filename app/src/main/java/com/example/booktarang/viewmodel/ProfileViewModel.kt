package com.example.booktarang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel: ViewModel() {
    private val _profileState = MutableLiveData<ApiState<User>>()
    val profileState get() = _profileState

    fun loadProfile() {
        var apiState = ApiState.loading<User>()
        _profileState.postValue(apiState)

        viewModelScope.launch {
            try {
                val response = ApiClient.get().apiService.loadProfile()
                if (response.isSuccess()) {
                    apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception) {
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {}
            _profileState.postValue(apiState)
        }
    }
}
