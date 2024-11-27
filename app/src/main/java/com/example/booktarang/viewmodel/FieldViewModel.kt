package com.example.booktarang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.api.ApiManager
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.State
import com.example.booktarang.model.Field
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FieldViewModel : ViewModel() {
    private val _fieldState = MutableLiveData<ApiState<List<Field>>>()
    val fieldState get() = _fieldState

    fun loadFields() {
        var apiState = ApiState.loading<List<Field>>()
        _fieldState.postValue(apiState)
       viewModelScope.launch {
           try {
               val response = ApiClient.get().apiService.loadFields()
               if (response.isSuccess()) {
                   apiState = ApiState.success(response.data)
               } else {
                   apiState = ApiState.error(response.message)
               }
           } catch (ex: Exception) {
               apiState = ApiState.error(ex.message)
           }

           withContext(Dispatchers.Main) {
               _fieldState.postValue(apiState)
           }
       }
    }
}