package com.example.booktarang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booktarang.databinding.ViewHolderBookingBinding
import com.example.booktarang.model.BookingData
import java.text.SimpleDateFormat
import java.util.Locale

class BookingAdapter: RecyclerView.Adapter<BookingViewHolder>() {

    private var data = emptyList<BookingData>()

    fun setData(data: List<BookingData>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderBookingBinding.inflate(layoutInflater, parent, false)

        return BookingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val bookingData = data[position]
        holder.bind(bookingData)
    }
}

class BookingViewHolder(private val binding: ViewHolderBookingBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(bookingData: BookingData) {
        binding.fieldFieldId.text = bookingData.field.name
        binding.fieldType.text = bookingData.field.sportType.name
        binding.fieldStartDate.text = bookingData.start_date
        binding.fieldEndDate.text = bookingData.end_date
    }

}