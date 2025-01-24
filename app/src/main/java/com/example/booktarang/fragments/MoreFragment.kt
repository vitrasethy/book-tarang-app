package com.example.booktarang.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.activity.LoginActivity
import com.example.booktarang.adapter.BookingAdapter
import com.example.booktarang.databinding.FragmentMoreBinding
import com.example.booktarang.global.AppEncryptPref
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.BookingData
import com.example.booktarang.model.State
import com.example.booktarang.model.User
import com.example.booktarang.viewmodel.AuthViewModel
import com.example.booktarang.viewmodel.BookingListViewModel

class MoreFragment: BaseFragment(){
    private val viewModel by viewModels<BookingListViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var binding: FragmentMoreBinding

    private val bookingLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> if (result.resultCode == Activity.RESULT_OK) {
            viewModel.loadBookingData()
    }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setUpUi()
        setUpListener()
    }

    private fun setupObserver() {
        authViewModel.checkLoginStatus(requireContext())
        authViewModel.isLoggedIn.observe(viewLifecycleOwner) {isLoggedIn ->
            if (!isLoggedIn) {
                showLoginButton()
                return@observe
            }
            viewModel.loadBookingData()
        }
        viewModel.bookingData.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun handleState(state: ApiState<List<BookingData>>) {
        when (state.state) {
            State.loading -> showLoading()
            State.success -> {
                hideLoading()
                showBookingList(state.data!!) }
            State.error -> {
                hideLoading()
                showAlert("Error", state.message ?: "Unexpected Error")
            }
            else -> {}
        }
    }

    private fun showBookingList(bookingList: List<BookingData>) {
        binding.lytLogin.isVisible = false
        binding.lytContent.isVisible = true

        val bookingAdapter = BookingAdapter();
        bookingAdapter.setData(bookingList)

        binding.recycleviewbooking.apply {
            adapter = bookingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpUi() {
        val token = AppEncryptPref.get().getToken(requireContext())
        if (token == null) {
            showLoginButton()
        } else {
            viewModel.loadBookingData()
        }
    }

    private fun setUpListener() {
        binding.btnLogin.setOnClickListener{
            onLoginButton()
        }
    }

    private fun onLoginButton() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        bookingLauncher.launch(intent)
    }


    private fun showLoginButton() {
        binding.lytContent.isVisible = false
        binding.lytLogin.isVisible = true
    }

}