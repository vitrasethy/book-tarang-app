package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booktarang.adapter.FieldAdapter
import com.example.booktarang.databinding.FragmentFieldBinding
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.FieldViewModel

class FieldFragment : Fragment() {
    private val viewModel by viewModels<FieldViewModel>()

    private lateinit var binding: FragmentFieldBinding
    private lateinit var FieldAdapter: FieldAdapter

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
        FieldAdapter = FieldAdapter()

        binding.recycleviewfield.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = FieldAdapter
        }

        viewModel.fieldState.observe(viewLifecycleOwner) { fieldState ->
            when (fieldState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    FieldAdapter.submitList(fieldState.data)
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
                else -> {}
            }
        }

        viewModel.loadFields()
    }

    private fun showLoading() {
        binding.lytContent.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.lytContent.visibility = View.VISIBLE
    }

    private fun showErrorContent() {
        binding.lytContent.visibility = View.GONE
        binding.lytError.visibility = View.VISIBLE
    }

}