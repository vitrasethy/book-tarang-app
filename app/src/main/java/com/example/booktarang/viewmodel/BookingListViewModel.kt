package com.example.booktarang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.BookingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookingListViewModel: ViewModel() {
    private val _bookingData = MutableLiveData<ApiState<List<BookingData>>>()
    val bookingData get() = _bookingData
    fun loadBookingData() {
        var apiState = ApiState.loading<List<BookingData>>()
        _bookingData.postValue(apiState)
        viewModelScope.launch {
           try {
                val response = ApiClient.get().apiService.loadBookings()
                if (response.isSuccess()) {
                     apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception) {
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _bookingData.postValue(apiState)
            }
        }
    }
}