package com.example.booktarang.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.booktarang.databinding.ActivityFieldShowBinding
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.FieldShowViewModel

class FieldShowActivity: BaseActivity() {
    private val viewModel by viewModels<FieldShowViewModel>()

    private lateinit var binding: ActivityFieldShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fieldId = intent.getIntExtra("FIELD_ID", -1)

        viewModel.fieldShowState.observe(this) {
            handleState(it)
        }

        viewModel.loadField(fieldId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setUpUi() {
        binding = ActivityFieldShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleState(state: ApiState<Field>) {
        when (state.state) {
            State.loading -> showLoading()
            State.success -> {
                hideLoading()
                showFields(state.data!!)
            }
            State.error -> {
                hideLoading()
                showError()
            }
            else -> {}
        }
    }

    private fun showFields(field: Field) {
        binding.txtFieldName.text = field.name
    }

    private fun showError() {}
}