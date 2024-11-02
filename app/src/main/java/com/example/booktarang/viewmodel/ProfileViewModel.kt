package com.example.booktarang.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiManager
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.State
import com.example.booktarang.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val _profileState = MutableLiveData<ApiState<User>>()
    val profileState: LiveData<ApiState<User>> get() = _profileState

    fun loadProfile() {
        val apiService = ApiManager.getApiService()

        _profileState.postValue(ApiState(State.loading, null))
        viewModelScope.launch {
            try {
                delay(3000)
                val profileResponse = apiService.loadProfile()
                if(profileResponse.isSuccess()) {
                    _profileState.postValue(ApiState(State.success, profileResponse.data))
                } else {
                    _profileState.postValue(ApiState(State.error, null))
                }

            } catch (ex: Exception) {
                Log.e("error", "Error $ex")
                _profileState.postValue(ApiState(State.error, null))
            }
        }
    }
}
