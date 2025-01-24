package com.example.booktarang.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktarang.adapter.FieldAdapter
import com.example.booktarang.databinding.ActivityFieldIndexBinding
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.Field
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.FieldIndexViewModel

class FieldIndexActivity: BaseActivity() {
    private val viewModel by viewModels<FieldIndexViewModel>()

    private lateinit var binding: ActivityFieldIndexBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sportTypeId = intent.getIntExtra("SPORT_TYPE_ID", -1)

        viewModel.fieldIndexState.observe(this) {
            handleState(it)
        }

        viewModel.loadFieldsBySportType(sportTypeId);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setUpUi() {
        binding = ActivityFieldIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                showError()
            }
            else -> {}
        }
    }

    private fun showFields(fields: List<Field>) {
        val fieldAdapter = FieldAdapter{ selectedField ->
            val intent = Intent(baseContext, FieldShowActivity::class.java).apply {
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

    private fun showError() {}

}