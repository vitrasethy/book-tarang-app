package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.adapter.SportTypeAdapter
import com.example.booktarang.databinding.FragmentHomeBinding
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Data
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.HomeViewModel

class HomeFragment: BaseFragment() {
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

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

        viewModel.homeState.observe(viewLifecycleOwner){ state ->
            handleState(state)
        }
        viewModel.loadData()
    }

    private fun handleState(state: ApiState<List<Data>>) {
        when(state.state) {
            State.loading -> showLoading()
            State.success -> {
                hideLoading()
                showSportTypes(state.data!!)
            }
            State.error -> {
                hideLoading()
            }
            else -> {}
        }
    }

    private fun showSportTypes(sportTypes: List<Data>) {
        val spanCount = 2

        val sportTypeLayoutManager = GridLayoutManager(requireContext(), spanCount)
        val sportTypeAdapter = SportTypeAdapter()
        sportTypeAdapter.setData(sportTypes)

        binding.recycleviewsport.apply {
            adapter = sportTypeAdapter
            layoutManager = sportTypeLayoutManager
        }
    }
}