package com.example.booktarang.model

data class ApiState<T>(
    val state: State?,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> loading(): ApiState<T> {
            return ApiState(State.loading, null,null)
        }
        fun <T> success(data: T?): ApiState<T> {
            return ApiState(State.success, null,data)
        }
        fun <T> error(message: String?): ApiState<T> {
            return ApiState(State.error, message,null)
        }
        fun <T> none(): ApiState<T> {
            return ApiState(State.none, null,null)
        }
    }
}

enum class State {
    none, loading, success, error
}

