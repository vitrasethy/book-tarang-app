package com.example.booktarang.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.activity.FieldIndexActivity
import com.example.booktarang.activity.FieldShowActivity
import com.example.booktarang.adapter.FieldAdapter
import com.example.booktarang.adapter.SportTypeAdapter
import com.example.booktarang.databinding.FragmentHomeBinding
import com.example.booktarang.global.AppEncryptPref
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Data
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.model.User
import com.example.booktarang.viewmodel.FieldIndexViewModel
import com.example.booktarang.viewmodel.FieldViewModel
import com.example.booktarang.viewmodel.HomeViewModel
import com.example.booktarang.viewmodel.ProfileViewModel

class HomeFragment: BaseFragment() {
    private val viewModel by viewModels<HomeViewModel>()
    private val fieldViewModel by viewModels<FieldViewModel>()

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
        fieldViewModel.fieldState.observe(viewLifecycleOwner){state -> handleFieldState(state)}
        fieldViewModel.loadFields()
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

    private fun handleFieldState(state: ApiState<List<Field>>) {
        when(state.state) {
            State.loading -> showLoading()
            State.success -> {
                hideLoading()
                showFields(state.data!!)
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
        val sportTypeAdapter = SportTypeAdapter { selectedSportType ->
            val intent = Intent(requireContext(), FieldIndexActivity::class.java).apply {
                putExtra("SPORT_TYPE_ID", selectedSportType.id)
            }
            startActivity(intent)
        }
        sportTypeAdapter.setData(sportTypes)

        binding.recycleviewsport.apply {
            adapter = sportTypeAdapter
            layoutManager = sportTypeLayoutManager
        }
    }

    private fun showFields(fields: List<Field>) {
        val fieldAdapter = FieldAdapter{ selectedField ->
            val intent = Intent(context, FieldShowActivity::class.java).apply {
                putExtra("FIELD_ID", selectedField.id)
            }
            startActivity(intent)
        }
        fieldAdapter.setData(fields)

        binding.recycleviewfield.apply {
            adapter = fieldAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}