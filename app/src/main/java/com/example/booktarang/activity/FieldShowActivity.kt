package com.example.booktarang.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.booktarang.activity.BaseActivity
import com.example.booktarang.databinding.ActivityFieldShowBinding
import com.example.booktarang.model.ApiState
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.booktarang.model.BookingResponse
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.BookingViewModel
import com.example.booktarang.viewmodel.FieldShowViewModel

class FieldShowActivity: BaseActivity() {
    private val fieldViewModel by viewModels<FieldShowViewModel>()
    private val bookingViewModel by viewModels<BookingViewModel>()
    private lateinit var binding: ActivityFieldShowBinding
    private var fieldId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding first
        binding = ActivityFieldShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get field ID from intent and check if it's valid
        fieldId = intent.getIntExtra("FIELD_ID", -1)
        Log.d("FieldShowActivity", "Received Field ID: $fieldId")

        // Setup UI components
        setupToolbar()
        setupObservers()
        setupDatePickers()
        setupBookingButton()

        // Load field data
        if (fieldId != -1) {
            fieldViewModel.loadField(fieldId)
        } else {
            Log.e("FieldShowActivity", "Invalid Field ID received")
            Toast.makeText(this, "Invalid Field ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Venue Details"
        }
    }

    private fun setupObservers() {
        fieldViewModel.fieldShowState.observe(this) { state ->
            handleFieldState(state)
        }

        bookingViewModel.bookingState.observe(this) { state ->
            handleBookingState(state)
        }
    }

    private fun setupDatePickers() {
        setupDateTimePicker(binding.startDateInput, "Select Start Date and Time")
        setupDateTimePicker(binding.endDateInput, "Select End Date and Time")
    }

    private fun setupBookingButton() {
        binding.btnBookVenue.setOnClickListener {
            handleBookingSubmit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun handleFieldState(state: ApiState<Field>) {
        when (state.state) {
            State.loading -> {
                showLoading()
            }
            State.success -> {
                hideLoading()
                state.data?.let { field ->
                    showFields(field)
                }
            }
            State.error -> {
                hideLoading()
                showError(state.message ?: "Error loading field details")
            }
            else -> {}
        }
    }

    private fun handleBookingState(state: ApiState<BookingResponse>) {
        when (state.state) {
            State.loading -> {
                showLoading()
                binding.btnBookVenue.isEnabled = false
            }
            State.success -> {
                hideLoading()
                binding.btnBookVenue.isEnabled = true
                Toast.makeText(this, "Booking successful!", Toast.LENGTH_SHORT).show()
                finish()
            }
            State.error -> {
                hideLoading()
                binding.btnBookVenue.isEnabled = true
                showError(state.message ?: "Booking failed")
            }
            else -> {}
        }
    }

    private fun showFields(field: Field) {
        binding.txtFieldName.text = field.name
        supportActionBar?.title = field.name ?: "Venue Details"
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupDateTimePicker(editText: TextInputEditText, title: String) {
        editText.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Select time")
                    .build()

                timePicker.addOnPositiveButtonClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selectedDate

                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                    calendar.set(Calendar.MINUTE, timePicker.minute)
                    calendar.set(Calendar.SECOND, 0)

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val formattedDateTime = dateFormat.format(calendar.time)
                    editText.setText(formattedDateTime)
                }

                timePicker.show(supportFragmentManager, "TIME_PICKER")
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }
    }

    private fun handleBookingSubmit() {
        val startDateTime = binding.startDateInput.text.toString()
        val endDateTime = binding.endDateInput.text.toString()

        if (startDateTime.isEmpty() || endDateTime.isEmpty()) {
            Toast.makeText(this, "Please select both start and end date/time", Toast.LENGTH_SHORT).show()
            return
        }

        bookingViewModel.booking(fieldId, startDateTime, endDateTime)
    }
}