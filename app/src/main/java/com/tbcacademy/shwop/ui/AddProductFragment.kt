package com.tbcacademy.shwop.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.AddProductFragmentBinding
import com.tbcacademy.shwop.ui.main.fragments.CreatePostFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment :
    BaseFragment<AddProductFragmentBinding>(AddProductFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSell.setOnClickListener {
            val action = CreatePostFragmentDirections.globalActionToCreatePostFragment()
            activity?.findNavController(R.id.mainContainer)?.navigate(action)

        }
    }


}