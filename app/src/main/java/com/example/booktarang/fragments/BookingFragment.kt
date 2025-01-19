package com.example.booktarang.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktarang.databinding.FragmentBookingBinding
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.BookingViewModel
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingFragment : Fragment() {
    private lateinit var binding: FragmentBookingBinding
    private val calendar = Calendar.getInstance()
    private var field: Field? = null
    private val viewModel: BookingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get field from arguments
        field = arguments?.getParcelable("field")

        // Setup date pickers
        setupDatePicker(binding.startDateInput, "Select Start Date")
        setupDatePicker(binding.endDateInput, "Select End Date")

        // Observe booking state
        viewModel.bookingState.observe(viewLifecycleOwner) { state ->
            when (state.state) {
                State.loading -> {
                    // Show loading indicator
                    binding.progressBar.visibility = View.VISIBLE
                    binding.submitButton.isEnabled = false
                }
                State.success -> {
                    // Hide loading indicator
                    binding.progressBar.visibility = View.GONE
                    binding.submitButton.isEnabled = true

                    // Show success message
                    Toast.makeText(context, "Booking successful!", Toast.LENGTH_SHORT).show()

                    // Go back to previous fragment
                    parentFragmentManager.popBackStack()
                }
                State.error -> {
                    // Hide loading indicator
                    binding.progressBar.visibility = View.GONE
                    binding.submitButton.isEnabled = true

                    // Show error message
                    Toast.makeText(context, state.message ?: "Booking failed", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        // Handle submit button click
        binding.submitButton.setOnClickListener {
            val startDate = binding.startDateInput.text.toString()
            val endDate = binding.endDateInput.text.toString()

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(context, "Please select both dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Submit booking through ViewModel
            field?.getId()?.let { fieldId ->
                viewModel.booking(fieldId, startDate, endDate)
            } ?: run {
                Toast.makeText(context, "Invalid field data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDatePicker(editText: TextInputEditText, title: String) {
        editText.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    editText.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                this.setTitle(title)
                // Set minimum date to today
                datePicker.minDate = System.currentTimeMillis() - 1000
                show()
            }
        }
    }
}