package com.example.booktarang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.booktarang.databinding.ViewHolderFieldBinding
import com.example.booktarang.model.Field

class FieldAdapter(private val onItemClick: (Field) -> Unit) : RecyclerView.Adapter<FieldAdapter.FieldViewHolder>() {

    private var data = emptyList<Field>()

    fun setData(data: List<Field>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderFieldBinding.inflate(layoutInflater, parent, false)

        return FieldViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val field = data[position]
        holder.bind(field)
    }

    inner class FieldViewHolder(private val binding: ViewHolderFieldBinding,
                                private val onItemClick: (Field) -> Unit): ViewHolder(binding.root) {
        fun bind(field: Field) {
            binding.fieldName.text = field.name
            binding.fieldType.text = field.sportType.name
            binding.fieldOpenTime.text = field.open_time
            binding.fieldCloseTime.text = field.close_time

            binding.root.setOnClickListener{onItemClick(field)}
        }
    }
}