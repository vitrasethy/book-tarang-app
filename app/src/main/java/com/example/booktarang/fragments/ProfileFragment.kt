package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktarang.databinding.FragmentProfileBinding
import com.example.booktarang.model.User
import com.example.booktarang.viewmodel.ProfileViewModel

class ProfileFragment: Fragment() {
    private val viewModel by viewModels<ProfileViewModel>()

    private lateinit var binding: FragmentProfileBinding

    var profile: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root;
    }

    private fun showLoading() {
//        binding
    }
}