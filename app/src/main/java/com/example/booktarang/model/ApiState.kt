package com.example.booktarang.model

data class ApiState<T>(
    val state: State,
    val data: T?
)

enum class State {
    none, loading, success, error
}

