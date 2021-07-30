package com.tbcacademy.shwop.ui.auth.onboarding.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentViewPagerBinding
import com.tbcacademy.shwop.ui.auth.onboarding.adapter.ViewPagerAdapter
import com.tbcacademy.shwop.ui.auth.onboarding.screens.FirstScreen
import com.tbcacademy.shwop.ui.auth.onboarding.screens.SecondScreen
import com.tbcacademy.shwop.ui.auth.onboarding.screens.ThirdScreen
import kotlinx.android.synthetic.main.fragment_view_pager.*


class ViewPagerFragment :
    BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPagerFragments()


    }

    private fun initPagerFragments() {
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()

        )

        val adapter = ViewPagerAdapter(
            fragmentList, childFragmentManager, lifecycle
        )
        binding.viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

    }


}