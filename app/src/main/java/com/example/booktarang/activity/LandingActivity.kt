package com.example.booktarang.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import com.example.booktarang.R
import com.example.booktarang.databinding.ActivityLandingBinding
import com.example.booktarang.fragments.FieldFragment
import com.example.booktarang.fragments.HomeFragment
import com.example.booktarang.fragments.MoreFragment
import com.example.booktarang.fragments.ProfileFragment

class LandingActivity: BaseActivity() {
    private lateinit var binding: ActivityLandingBinding

    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val fieldFragment = FieldFragment()
    private val moreFragment = MoreFragment()

    private lateinit var activityFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpFragment()
        setUpBottomNavigation()
    }

    private fun setUpFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        activityFragment = homeFragment

        fragmentTransaction.add(binding.lytFragment.id, homeFragment)
        fragmentTransaction.add(binding.lytFragment.id, fieldFragment).hide(fieldFragment)
        fragmentTransaction.add(binding.lytFragment.id, profileFragment).hide(profileFragment)
        fragmentTransaction.add(binding.lytFragment.id, moreFragment).hide(moreFragment)

        fragmentTransaction.commit()
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            handleOnNavigationItemSelected(it)
        }
    }

    private fun handleOnNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuHome -> showFragment(homeFragment)
            R.id.mnuFootball -> showFragment(fieldFragment)
            R.id.mnuAccount -> showFragment(profileFragment)
            else -> showFragment(moreFragment)
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