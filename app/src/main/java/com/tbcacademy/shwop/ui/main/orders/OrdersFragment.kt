package com.tbcacademy.shwop.ui.main.orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentOrdersBinding
import com.tbcacademy.shwop.ui.main.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    @Inject
    lateinit var cartAdapter: CartAdapter

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        init()

       /* if ((adapter as Any) == 0) {
            binding.cartRecycler.isVisible = true
        }*/

      /*  if (cartAdapter.differ.currentList.isNullOrEmpty()) {
            binding.cartRecycler.isVisible = false


        }*/

        //binding.ivEmptyCartt.isVisible = cartAdapter.differ.currentList.isEmpty()


    }

    private fun init() {

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posotion = viewHolder.absoluteAdapterPosition
                val cart = cartAdapter.differ.currentList[posotion]
                viewModel.deleteProduct(cart)
                view?.let {

                    Snackbar.make(
                        it,
                        getString(R.string.successfully_deleted),
                        Snackbar.LENGTH_LONG
                    )
                        .apply {
                            setAction("Undo") {
                                viewModel.saveProduct(cart)

                            }
                            show()
                        }
                }

            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.cartRecycler)
        }

        viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer { characters ->
            cartAdapter.differ.submitList(characters)
        })

    }

    private fun setupRecyclerView() = binding.cartRecycler.apply {
        adapter = cartAdapter
        layoutManager = LinearLayoutManager(requireContext())
        itemAnimator = null

     //   binding.ivEmptyCartt.isVisible = (adapter as Any) == 0






    }


}