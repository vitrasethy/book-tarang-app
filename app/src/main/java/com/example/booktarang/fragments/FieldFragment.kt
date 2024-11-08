package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktarang.databinding.FragmentFieldBinding
import com.example.booktarang.viewmodel.FieldViewModel

class FieldFragment : Fragment() {
    private val viewModel by viewModels<FieldViewModel>()

    private lateinit var binding: FragmentFieldBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showLoading() {
        // binding
    }
}