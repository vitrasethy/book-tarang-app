package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booktarang.adapter.SportTypeAdapter
import com.example.booktarang.databinding.FragmentHomeBinding
import com.example.booktarang.databinding.FragmentProfileBinding
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.HomeViewModel

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var SportTypeAdapter: SportTypeAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SportTypeAdapter = SportTypeAdapter()

        binding.recycleviewsport.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = SportTypeAdapter
        }

        viewModel.dateState.observe(viewLifecycleOwner){ dataState ->
            when(dataState.state){
                State.success -> SportTypeAdapter.submitList(dataState.data)
                State.error -> Toast.makeText(context, "Error loading data. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loadData()
    }


}