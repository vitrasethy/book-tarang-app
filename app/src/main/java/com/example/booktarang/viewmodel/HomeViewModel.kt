package com.example.booktarang.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Data
import com.example.booktarang.model.State
import com.example.booktarang.service.ApiManager
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _dataState = MutableLiveData<ApiState<List<Data>>>()
    val dateState: LiveData<ApiState<List<Data>>> get() = _dataState
    fun loadData() {
        val dataService = ApiManager.getDataService()
        viewModelScope.launch {
            try {
                val dataResponse = dataService.loadDataDisplay()
                if(dataResponse.status == "successful"){
                    _dataState.postValue(ApiState(State.success, dataResponse.data))
                }else{
                    _dataState.postValue(ApiState(State.error, null))
                }
            }catch (ex: Exception){
                Log.e("ruppite", "Error While loading data: $ex")
            }
        }
    }
}
