package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.adapter.BookingAdapter
import com.example.booktarang.databinding.FragmentMoreBinding
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.BookingData
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.BookingListViewModel

class MoreFragment: Fragment() {

    private val viewModel by viewModels<BookingListViewModel>()

    private lateinit var binding: FragmentMoreBinding

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

        setupUi()
        setupListener()
        setupObserver()
        viewModel.loadBookingData()
    }

    private fun setupObserver() {
        viewModel.bookingData.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun handleState(state: ApiState<List<BookingData>>) {
        when (state.state) {
            State.success -> { showBookingList(state.data!!) }
            State.error -> {}
            else -> {}
        }
    }

    private fun showBookingList(bookingList: List<BookingData>) {
        binding.recycleviewbooking.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleviewbooking.adapter = BookingAdapter(bookingList)
    }

    private fun setupListener() {
    }

    private fun setupUi() {
    }


}