package com.example.booktarang.fragments

import androidx.fragment.app.Fragment
import com.example.booktarang.activity.BaseActivity

open class BaseFragment: Fragment() {

    fun showLoading() {
        val hostActivity = activity as BaseActivity?
        hostActivity?.showLoading()
    }

    fun hideLoading() {
        val hostActivity = activity as BaseActivity?
        hostActivity?.hideLoading()
    }

    fun showAlert(title: String, messaage: String) {
        val hostActivity = activity as BaseActivity
        hostActivity.showAlert(title, messaage)
    }

}