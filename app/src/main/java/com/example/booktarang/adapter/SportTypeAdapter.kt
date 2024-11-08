package com.example.booktarang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booktarang.databinding.ViewHolderSporttypeBinding
import com.example.booktarang.model.Data

class SportTypeAdapter: ListAdapter<Data, SportTypeAdapter.SportTypeViewHolder>(SportTypeDiffCallback()) {
    class SportTypeViewHolder(private var binding: ViewHolderSporttypeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data){
            binding.sportName.text = data.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportTypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSporttypeBinding.inflate(inflater, parent, false)
        return SportTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SportTypeViewHolder, position: Int) {
        val Data = getItem(position)
        holder.bind(Data)
    }
}

class SportTypeDiffCallback: DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

}
