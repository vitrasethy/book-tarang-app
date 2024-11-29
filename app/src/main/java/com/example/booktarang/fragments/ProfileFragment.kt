package com.example.booktarang.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktarang.activity.LoginActivity
import com.example.booktarang.databinding.FragmentProfileBinding
import com.example.booktarang.global.AppEncryptPref
import com.example.booktarang.model.ApiState
import com.example.booktarang.model.State
import com.example.booktarang.model.User
import com.example.booktarang.viewmodel.ProfileViewModel

class ProfileFragment: BaseFragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.loadProfile()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lytContent.isVisible = false
        binding.lytLogin.isVisible = false

        viewModel.profileState.observe(viewLifecycleOwner) {state ->
            handleState(state)
        }
        setUpUi()
        setUpListener()
    }

    private fun handleState(state: ApiState<User>) {
        when(state.state) {
            State.loading -> showLoading()
            State.success -> {
                hideLoading()
                showProfile(state.data!!)
            }
            State.error -> {
                hideLoading()
                showAlert("Test", state.message!!)
            }
            else -> {}
        }
    }

    private fun setUpUi() {
        val token = AppEncryptPref.get().getToken(requireContext())
        if (token == null) {
            showLoginButton()
        } else {
            viewModel.loadProfile();
        }
    }

    private fun setUpListener() {
        binding.btnLogin.setOnClickListener{
            onLoginButton()
        }
        binding.btnLogOut.setOnClickListener{
            onLogOutButton()
        }
    }

    private fun onLoginButton() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        loginLauncher.launch(intent)
    }

    private fun onLogOutButton() {
        AppEncryptPref.get().deleteToken(requireContext())
        showLoginButton()
    }

    private fun showProfile(user: User) {
        binding.lytLogin.isVisible = false
        binding.lytContent.isVisible = true

        binding.txtName.text = user.name
        binding.txtNameMain.text = user.name
        binding.txtEmail.text = user.email
    }

    private fun showLoginButton() {
        binding.lytContent.isVisible = false
        binding.lytLogin.isVisible = true
    }
}