package com.example.booktarang.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktarang.databinding.FragmentProfileBinding
import com.example.booktarang.model.State
import com.example.booktarang.model.User
import com.example.booktarang.viewmodel.ProfileViewModel

class ProfileFragment: Fragment() {
    private val viewModel by viewModels<ProfileViewModel>()

    private lateinit var binding: FragmentProfileBinding

    var profile: User? = null

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

        Log.d("ruppite", "[ProfileFragmentKotlin] onViewCreated")

        // Observe to ViewModel state
        viewModel.profileState.observe(viewLifecycleOwner) { profileState ->
            when(profileState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    displayProfile(profileState.data!!)
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
                else -> {}
            }
        }

        // Forward event to ViewModel
        if(profile != null) {
            displayProfile(profile!!)
        } else {
            viewModel.loadProfile()
        }

    }


    private fun displayProfile(profile: User) {
        binding.txtName.text = profile.name
//        Picasso.get()
//            .load(profile.coverImage)
//            .into(binding.imgCover)
//        Picasso.get()
//            .load(profile.profileImage)
//            .into(binding.imgProfile)
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