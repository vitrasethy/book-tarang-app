package com.example.booktarang.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booktarang.global.AppEncryptPref

class AuthViewModel: ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn get() = _isLoggedIn

    fun checkLoginStatus(context: Context) {
        val token = AppEncryptPref.get().getToken(context)
        _isLoggedIn.value = token != null
    }

    fun updateLoginStatus(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
    }
}