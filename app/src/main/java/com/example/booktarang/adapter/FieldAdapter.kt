package com.example.booktarang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.booktarang.databinding.ViewHolderFieldBinding
import com.example.booktarang.model.Field

class FieldAdapter: ListAdapter<Field, FieldAdapter.FieldViewHolder>(FieldTypeDiffCallback()) {
    class FieldViewHolder(private var binding: ViewHolderFieldBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Field) {
            binding.fieldName.text = data.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderFieldBinding.inflate(inflater, parent, false)
        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class FieldTypeDiffCallback: DiffUtil.ItemCallback<Field>() {
    override fun areItemsTheSame(oldItem: Field, newItem: Field): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Field, newItem: Field): Boolean {
        return oldItem.id == newItem.id
    }
}
