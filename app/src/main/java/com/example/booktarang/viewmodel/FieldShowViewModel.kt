package com.example.booktarang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Field
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FieldShowViewModel: ViewModel() {
    private val _fieldShowState = MutableLiveData<ApiState<Field>>()
    val fieldShowState get() = _fieldShowState

    fun loadField(fieldId: Int) {
        var apiState = ApiState.loading<Field>()
        _fieldShowState.postValue(apiState)
        viewModelScope.launch {
            try {
                val response = ApiClient.get().apiService.loadField(fieldId);
                if (response.isSuccess()) {
                    apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception){
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _fieldShowState.postValue(apiState)
            }
        }
    }
}