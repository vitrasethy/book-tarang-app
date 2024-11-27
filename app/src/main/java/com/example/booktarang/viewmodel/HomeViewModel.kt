package com.example.booktarang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {
    private val _homeState = MutableLiveData<ApiState<List<Data>>>()
    val homeState get() = _homeState
    fun loadData() {
        var apiState = ApiState.loading<List<Data>>()
        _homeState.postValue(apiState)
        viewModelScope.launch {
            try {
                val dataResponse = ApiClient.get().apiService.loadDataDisplay()
                if(dataResponse.isSuccess()){
                    apiState = ApiState.success(dataResponse.data)
                }else{
                    apiState = ApiState.error(dataResponse.message)
                }
            }catch (ex: Exception){
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _homeState.postValue(apiState)
            }
        }
    }
}
