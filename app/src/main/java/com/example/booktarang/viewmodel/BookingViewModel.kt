package com.example.booktarang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktarang.api.ApiClient
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.BookingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookingViewModel: ViewModel() {
    private val _bookingState = MutableLiveData<ApiState<BookingResponse>>()
    val bookingState get() = _bookingState

    fun booking(fieldId: Int,startDate: String, endDate: String) {
        var apiState = ApiState.loading<BookingResponse>()
        _bookingState.postValue(apiState)
        viewModelScope.launch {
            try {
                val response = ApiClient.get().apiService.bookings(fieldId, endDate, startDate)
                if (response.isSuccess()) {
                    apiState = ApiState.success(response.data)
                } else {
                    apiState = ApiState.error(response.message)
                }
            } catch (ex: Exception) {
                apiState = ApiState.error(ex.message)
            }

            withContext(Dispatchers.Main) {
                _bookingState.postValue(apiState)
            }
        }
    }
}