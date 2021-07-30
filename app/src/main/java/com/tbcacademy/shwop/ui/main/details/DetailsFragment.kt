package com.tbcacademy.shwop.ui.main.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.DetailsFragmentBinding
import com.tbcacademy.shwop.base.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsFragmentBinding>(DetailsFragmentBinding::inflate) {

    private val detailArg by navArgs<DetailsFragmentArgs>()

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val details = detailArg.details


        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnPurchase.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_purchaseFragment)
        }

        showData()
        binding.btnAddToCart.setOnClickListener {

            viewModel.saveProduct(details)
            snackbar("The product was successfully added to the cart")

        }


    }

    private fun showData() {
        val details = detailArg.details

        Glide.with(this).load(details.imageUrl).into(binding.ivProductImage)
        binding.tvDescrip.text = details.text
        binding.tvProduct.text = details.text
        binding.tvPrice.text = "₾ ${details.price}"


    }

}