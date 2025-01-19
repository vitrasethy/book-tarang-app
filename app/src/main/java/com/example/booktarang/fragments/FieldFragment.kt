package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.R
import com.example.booktarang.adapter.FieldAdapter
import com.example.booktarang.databinding.FragmentFieldBinding
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.FieldViewModel

class FieldFragment : BaseFragment(), FieldAdapter.OnBookingClickListener {
    private val viewModel by viewModels<FieldViewModel>()
    private lateinit var binding: FragmentFieldBinding
    private lateinit var fieldAdapter: FieldAdapter

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

        setupRecyclerView()

        viewModel.fieldState.observe(viewLifecycleOwner) { fieldState ->
            handleState(fieldState)
        }

        viewModel.loadFields()
    }

    private fun setupRecyclerView() {
        fieldAdapter = FieldAdapter(this)
        binding.recycleviewfield.apply {
            adapter = fieldAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
        fieldAdapter.setData(fields)
    }

    private fun showErrorContent() {
        binding.lytContent.visibility = View.GONE
        binding.lytError.visibility = View.VISIBLE
    }

    override fun onBookClick(field: Field) {
        // Create and show the BookingFragment
        val bookingFragment = BookingFragment().apply {
            arguments = Bundle().apply {
                putParcelable("field", field)
            }
        }

        // Replace current fragment with BookingFragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.lytFragment, bookingFragment)
            .addToBackStack(null)
            .commit()
    }
}