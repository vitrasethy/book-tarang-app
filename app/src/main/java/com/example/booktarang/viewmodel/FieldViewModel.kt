package com.example.booktarang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiManager
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.State
import com.example.booktarang.model.Field
import kotlinx.coroutines.launch

class FieldViewModel : ViewModel() {
    private val _fieldState = MutableLiveData<ApiState<List<Field>>>()
    val fieldState: LiveData<ApiState<List<Field>>> get() = _fieldState

    fun loadFields() {
        val apiService = ApiManager.getApiService()
        _fieldState.postValue(ApiState(State.loading, null))
        viewModelScope.launch {
            try {
                val fieldResponse = apiService.loadFields()
                if (fieldResponse.isSuccess()) {
                    _fieldState.postValue(ApiState(State.success, fieldResponse.data))
                } else {
                    _fieldState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                _fieldState.postValue(ApiState(State.error, null))
            }
        }
    }
}