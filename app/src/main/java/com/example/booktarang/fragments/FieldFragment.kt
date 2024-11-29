package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.adapter.FieldAdapter
import com.example.booktarang.databinding.FragmentFieldBinding
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.FieldViewModel

class FieldFragment : BaseFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fieldState.observe(viewLifecycleOwner) { fieldState ->
            handleState(fieldState)
        }

        viewModel.loadFields()
    }

    private fun handleState(state: ApiState<List<Field>>) {
        when (state.state) {
            State.loading -> showLoading()
            State.success -> {
                hideLoading()
                showFields(state.data!!)
            }
            State.error -> {
                hideLoading()
                showErrorContent()
            }
            else -> {}
        }
    }

    private fun showFields(fields: List<Field>) {
        val fieldLayoutManager = LinearLayoutManager(requireContext())
        val fieldAdapter = FieldAdapter()
        fieldAdapter.setData(fields)

        binding.recycleviewfield.apply {
            adapter = fieldAdapter
            layoutManager = fieldLayoutManager
        }
    }

    private fun showErrorContent() {
        binding.lytContent.visibility = View.GONE
        binding.lytError.visibility = View.VISIBLE
    }

}