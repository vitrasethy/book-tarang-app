package com.example.booktarang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.booktarang.databinding.ViewHolderSporttypeBinding
import com.example.booktarang.model.Data

class SportTypeAdapter: RecyclerView.Adapter<SportTypeViewHolder>() {

    private var data = emptyList<Data>()

    fun setData(data: List<Data>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportTypeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSporttypeBinding.inflate(layoutInflater, parent, false)

        return SportTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SportTypeViewHolder, position: Int) {
        val sportType = data[position]
        holder.bind(sportType)
    }
}

class SportTypeViewHolder(private val binding: ViewHolderSporttypeBinding): ViewHolder(binding.root) {
    fun bind(sportType: Data) {
        binding.sportName.text = sportType.name
    }
}