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

class FieldIndexViewModel: ViewModel() {
    private val _fieldIndexState = MutableLiveData<ApiState<List<Field>>>()
    val fieldIndexState get() = _fieldIndexState

    fun loadFieldsBySportType(sportTypeId: Int) {
        var apiState = ApiState.loading<List<Field>>()
        _fieldIndexState.postValue(apiState)
        viewModelScope.launch {
            try {
                val response = ApiClient.get().apiService.loadFieldsBySportType(sportTypeId);
                if (response.isSuccess()) {
                    apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception){
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _fieldIndexState.postValue(apiState)
            }
        }
    }

}