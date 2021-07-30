package com.tbcacademy.shwop.ui.auth.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.data.UserPreference
import com.tbcacademy.shwop.databinding.FragmentThirdScreenBinding



class ThirdScreen : BaseFragment<FragmentThirdScreenBinding>(FragmentThirdScreenBinding::inflate) {

    private lateinit var sharedPreferences: UserPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFinish.setOnClickListener {
            sharedPreferences = UserPreference(requireContext())
            sharedPreferences.saveUserSession(true)
            sharedPreferences.saveToken("onBoard")

            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }
    }
}