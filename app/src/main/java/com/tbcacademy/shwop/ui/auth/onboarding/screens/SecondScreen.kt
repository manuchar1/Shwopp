package com.tbcacademy.shwop.ui.auth.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentSecondBinding


class SecondScreen : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.btnNext2.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

}