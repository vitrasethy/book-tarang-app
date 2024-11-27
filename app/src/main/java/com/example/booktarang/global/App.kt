package com.example.booktarang.global

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @Volatile
        private lateinit var instance: App

        fun get(): App = instance
    }
}