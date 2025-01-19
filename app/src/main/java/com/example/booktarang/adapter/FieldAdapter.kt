package com.example.booktarang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.booktarang.databinding.ViewHolderFieldBinding
import com.example.booktarang.model.Field

class FieldAdapter(private val listener: OnBookingClickListener) : Adapter<FieldAdapter.FieldViewHolder>() {
    interface OnBookingClickListener {
        fun onBookClick(field: Field)
    }

    private var data = emptyList<Field>()

    fun setData(data: List<Field>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderFieldBinding.inflate(layoutInflater, parent, false)
        return FieldViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val field = data[position]
        holder.bind(field, listener)
    }

    inner class FieldViewHolder(private val binding: ViewHolderFieldBinding): ViewHolder(binding.root) {
        fun bind(field: Field, listener: OnBookingClickListener) {
            binding.apply {
                // Use getter methods instead of direct property access
                fieldName.text = field.getName()
                fieldOpenTime.text = field.getOpenTime()
                fieldCloseTime.text = field.getCloseTime()

                submitBooking.setOnClickListener {
                    listener.onBookClick(field)
                }
            }
        }
    }
}