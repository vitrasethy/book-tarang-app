package com.example.booktarang.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Field(
     val id: Int,
     val name: String,
     val sportType: Data,
     val open_time: String,
     val close_time: String
)