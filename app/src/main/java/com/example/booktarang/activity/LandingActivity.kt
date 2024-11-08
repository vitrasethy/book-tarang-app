package com.example.booktarang.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.booktarang.R
import com.example.booktarang.databinding.ActivityLandingBinding
import com.example.booktarang.fragments.FieldFragment
import com.example.booktarang.fragments.HomeFragment
import com.example.booktarang.fragments.LoginFragment
import com.example.booktarang.fragments.ProfileFragment

class LandingActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    private val homeFragment = HomeFragment()
    private val loginFragment = LoginFragment()
    private val profileFragment = ProfileFragment()
    private val fieldFragment = FieldFragment()

    private lateinit var activityFragment: Fragment

    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        activityFragment = homeFragment
        fragmentTransaction.add(binding.lytFragment.id, homeFragment)
        fragmentTransaction.add(binding.lytFragment.id, loginFragment).hide(loginFragment)
        fragmentTransaction.add(binding.lytFragment.id, fieldFragment).hide(fieldFragment)

        fragmentTransaction.commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            handleOnNavigationItemSelected(it)
        }
    }

    fun showProfileFragment(token: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(loginFragment)
        fragmentTransaction.add(binding.lytFragment.id, profileFragment)
        fragmentTransaction.commit()
    }

    private fun handleOnNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuHome -> showFragment(homeFragment)
            R.id.mnuAccount -> showFragment(loginFragment)
            else -> showFragment(fieldFragment)
        }
        return true
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.hide(activityFragment)
        fragmentTransaction.show(fragment)
        activityFragment = fragment
        fragmentTransaction.commit()
    }

}