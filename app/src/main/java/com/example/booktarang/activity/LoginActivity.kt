package com.example.booktarang.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.booktarang.databinding.ActivityLoginBinding
import com.example.booktarang.global.AppEncryptPref
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.State
import com.example.booktarang.viewmodel.LoginViewModel

class LoginActivity: BaseActivity() {
    private val viewModel by viewModels<LoginViewModel>()

     private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()

        setSupportActionBar(binding.appToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpObserver()
        setUpListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }


    private fun setUpUi() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpListener() {
        binding.btnLogin.setOnClickListener {
            onLoginButtonClick()
        }
        binding.btnGoToRegister.setOnClickListener {
            onGoToRegisterButtonClick()
        }
    }

        private fun setUpObserver() {
            viewModel.loginState.observe(this) {
                handleState(it)
            }
        }

        private fun handleState(state: ApiState<LoginResponse>) {
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

        private fun onLoginButtonClick() {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Invalid Input", "Please enter username and password")
                return
            }
            viewModel.login(email, password)
        }

        private fun onGoToRegisterButtonClick() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
}