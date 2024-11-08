package com.example.booktarang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.booktarang.activity.LandingActivity
import com.example.booktarang.databinding.FragmentLoginBinding
import com.example.booktarang.model.State
import com.example.booktarang.model.User
import com.example.booktarang.viewmodel.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            onLoginButtonClick()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect{
                    when(it.state) {
                        State.loading -> showLoading()
                        State.success -> {
                            hideLoading()
                            showProfile(it.data!!.user)
                        }
                        State.error -> {
                            hideLoading()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun onLoginButtonClick() {
        val email = binding.editTextEmail.text.trim().toString()
        val password = binding.editPassword.text.trim().toString()
        if(email.isEmpty() || password.isEmpty()) {
            val dialog = MaterialAlertDialogBuilder(requireContext())
            dialog.setTitle("Invalid Input")
            dialog.setMessage("All fields are required")
            dialog.show()
            return
        }

        viewModel.login(email, password)
    }

    private fun showLoading() {
        binding.btnLogin.isVisible = false;
        binding.pgrLoading.isVisible = true;
    }

    private fun hideLoading() {
        binding.btnLogin.isVisible = true;
        binding.pgrLoading.isVisible = false;
    }

    private fun showProfile(profile: User) {
        if (activity is LandingActivity) {
            val landingActivity = activity as LandingActivity
            landingActivity.showProfileFragment(profile)
        }
    }
}