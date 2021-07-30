package com.tbcacademy.shwop.ui.bottom_navigation.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbcacademy.shwop.adapters.UserAdapter
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentSearchBinding
import com.tbcacademy.shwop.utils.Constants.SEARCH_TIME_DELAY
import com.tbcacademy.shwop.utils.EventObserver
import com.tbcacademy.shwop.base.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    @Inject
    lateinit var userAdapter: UserAdapter
    private val viewModel: SearchViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToObservers()
        searchUser()

    }


    private fun searchUser() {

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    viewModel.searchUser(it.toString())
                }
            }
        }

        userAdapter.setOnUserClickListener { user ->
            findNavController()
                .navigate(
                    SearchFragmentDirections.globalActionToOthersProfileFragment(user.uid)
                )
        }

    }

    private fun subscribeToObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.searchProgressBar.isVisible = false
                snackbar(it)
            },
            onLoading = {
                binding.searchProgressBar.isVisible = true
            }
        ) { users ->
            binding.searchProgressBar.isVisible = false
            userAdapter.users = users
        })
    }

    private fun setupRecyclerView() = binding.rvSearchResults.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = userAdapter
        itemAnimator = null
    }
}