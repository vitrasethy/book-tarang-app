package com.example.booktarang.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.booktarang.R
import com.example.booktarang.databinding.ActivityLandingBinding
import com.example.booktarang.fragments.HomeFragment
import com.example.booktarang.fragments.ProfileFragment

class LandingActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var activityFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        activityFragment = profileFragment
        fragmentTransaction.add(binding.lytFragment.id, homeFragment).hide(homeFragment)

        fragmentTransaction.commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            handleOnNavigationItemSelected(it)
        }
    }

    private fun handleOnNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuHome -> showFragment(homeFragment)
            else -> showFragment(profileFragment)
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