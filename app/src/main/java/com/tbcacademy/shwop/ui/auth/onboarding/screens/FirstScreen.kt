package com.tbcacademy.shwop.ui.auth.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentFirstScreenBinding
import com.tbcacademy.shwop.utils.LanguagePickerBottomSheet


class FirstScreen : BaseFragment<FragmentFirstScreenBinding>(FragmentFirstScreenBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }


        binding.tvLanguage.setOnClickListener {
            val languagePickerBottomSheet = LanguagePickerBottomSheet()
            languagePickerBottomSheet.show(childFragmentManager, "tag")

        }

    }



}