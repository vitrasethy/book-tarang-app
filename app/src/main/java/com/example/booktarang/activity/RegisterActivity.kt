package com.example.booktarang.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.booktarang.model.ApiState
import com.example.booktarang.databinding.ActivityRegisterBinding
import com.example.booktarang.global.AppEncryptPref
import com.example.booktarang.model.RegisterResponse
import com.example.booktarang.viewmodel.RegisterViewModel
import com.example.booktarang.model.State

class RegisterActivity : BaseActivity() {
    private val viewModel by viewModels<RegisterViewModel>()

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpObserver()
        setUpListener()
    }

    private fun setUpUi() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpListener() {
        binding.btnRegister.setOnClickListener {
            onRegisterButtonClick()
        }
    }

    private fun setUpObserver() {
        viewModel.registerState.observe(this) {
            handleState(it)
        }
    }

    private fun handleState(state: ApiState<RegisterResponse>) {
        when (state.state) {
            State.loading -> showLoading()
            State.success -> {
                AppEncryptPref.get().storeToken(this, state.data!!.token)
                setResult(RESULT_OK)
                finish()
            }

            State.error -> {
                hideLoading()
                showAlert("Error", state.message ?: "Unexpected Error")
            }

            else -> {}
        }
    }

    private fun onRegisterButtonClick() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val confirmationPassword = binding.editTextConfirmPassword.text.toString().trim()
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || confirmationPassword.isEmpty()) {
            showAlert("Invalid Input", "Please enter all details")
            return
        }
        viewModel.register(name, email, password, confirmationPassword)
    }
}