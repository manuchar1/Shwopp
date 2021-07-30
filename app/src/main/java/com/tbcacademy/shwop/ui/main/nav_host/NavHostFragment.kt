package com.tbcacademy.shwop.ui.main.nav_host

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentNavHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostFragment : BaseFragment<FragmentNavHostBinding>(FragmentNavHostBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)


        }

    }

}