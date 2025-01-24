package com.example.booktarang.fragments

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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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

        // Setup datetime pickers
        setupDateTimePicker(binding.startDateInput, "Select Start Date and Time")
        setupDateTimePicker(binding.endDateInput, "Select End Date and Time")

        // Observe booking state
        viewModel.bookingState.observe(viewLifecycleOwner) { state ->
            when (state.state) {
                State.loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.submitButton.isEnabled = false
                }
                State.success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.submitButton.isEnabled = true
                    Toast.makeText(context, "Booking successful!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                State.error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.submitButton.isEnabled = true
                    Toast.makeText(context, state.message ?: "Booking failed", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        // Handle submit button click
        binding.submitButton.setOnClickListener {
            val startDateTime = binding.startDateInput.text.toString()
            val endDateTime = binding.endDateInput.text.toString()

            if (startDateTime.isEmpty() || endDateTime.isEmpty()) {
                Toast.makeText(context, "Please select both start and end date/time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Submit booking through ViewModel
            field?.getId()?.let { fieldId ->
                viewModel.booking(fieldId, startDateTime, endDateTime)
            } ?: run {
                Toast.makeText(context, "Invalid field data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDateTimePicker(editText: TextInputEditText, title: String) {
        editText.setOnClickListener {
            // Show Date Picker first
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                // After date is selected, show Time Picker
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Select time")
                    .build()

                timePicker.addOnPositiveButtonClickListener {
                    // Create calendar instance
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selectedDate

                    // Set the selected time
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                    calendar.set(Calendar.MINUTE, timePicker.minute)
                    calendar.set(Calendar.SECOND, 0)

                    // Format the final datetime
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val formattedDateTime = dateFormat.format(calendar.time)
                    editText.setText(formattedDateTime)
                }

                timePicker.show(parentFragmentManager, "TIME_PICKER")
            }

            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }
    }
}