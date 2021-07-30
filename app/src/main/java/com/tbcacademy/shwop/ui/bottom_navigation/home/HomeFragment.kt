package com.tbcacademy.shwop.ui.bottom_navigation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.databinding.FragmentHomeBinding
import com.tbcacademy.shwop.ui.main.details.DetailsFragmentDirections
import com.tbcacademy.shwop.ui.main.details.DetailsViewModel
import com.tbcacademy.shwop.ui.main.fragments.BasePostFragment
import com.tbcacademy.shwop.ui.main.product_to_cart.CartFragmentDirections
import com.tbcacademy.shwop.ui.main.viewmodels.BasePostViewModel
import com.tbcacademy.shwop.base.snackbar
import dagger.hilt.android.AndroidEntryPoint


import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BasePostFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    //  private val homeArg by navArgs()
    private val viewModell: DetailsViewModel by viewModels()
    val homeArg: HomeFragmentArgs by navArgs()

    override val basePostViewModel: BasePostViewModel
        get() {
            val vm: HomeViewModel by viewModels()
            return vm
        }

    private val viewModel: HomeViewModel by lazy { basePostViewModel as HomeViewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.pagingFlow.collect {
                postAdapter.submitData(it)
            }
        }

        binding.iconBtnCart.setOnClickListener {
            val action = CartFragmentDirections.actionGlobalCartFragment()
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
        }



        lifecycleScope.launch {
            postAdapter.loadStateFlow.collectLatest {
              /*  binding.allPostsProgressBar.isVisible = it.refresh is LoadState.Loading ||
                        it.append is LoadState.Loading*/

                if (it.refresh is LoadState.Loading ||
                    it.append is LoadState.Loading){

                    binding.rvAllPosts.showShimmer()
                }else{
                    binding.rvAllPosts.hideShimmer()
                }


            }
        }
        postAdapter.setOnPostClickListener {
            val action = DetailsFragmentDirections.actionGlobalDetailsFragment(it)
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
        }

        postAdapter.setOnCartClickListener {
            snackbar("clicked")
            //viewModell.saveProduct(home)
        }
    }


    private fun setupRecyclerView() = binding.rvAllPosts.apply {
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())
        itemAnimator = null
    }


}