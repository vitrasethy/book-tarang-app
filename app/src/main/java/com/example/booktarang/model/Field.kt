package com.example.booktarang.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Field(
    private val id: Int,
    private val name: String,
    private val open_time: String,
    private val close_time: String
) : Parcelable {
    fun getId(): Int = id
    fun getName(): String = name
    fun getOpenTime(): String = open_time
    fun getCloseTime(): String = close_time
}