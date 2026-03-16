package com.example.projecthk2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

open class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).visibility = View.GONE
    }

    // ĐỔI onStop → onPause để hiện lại toolbar sớm hơn khi back
    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar?.show()
        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar).visibility = View.VISIBLE
    }
}