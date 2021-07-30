package com.tbcacademy.shwop.ui.main.product_to_cart


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.CartFragmentBinding

class CartFragment : BaseFragment<CartFragmentBinding>(CartFragmentBinding::inflate) {

   // private val detailArg by navArgs<DetailsFragmentArgs>()

    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}