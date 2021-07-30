package com.tbcacademy.shwop.ui.bottom_navigation.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.utils.EventObserver
import com.tbcacademy.shwop.ui.main.viewmodels.BasePostViewModel
import com.tbcacademy.shwop.base.snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwop.databinding.FragmentProfileBinding
import com.tbcacademy.shwop.ui.main.fragments.BasePostFragment
import com.tbcacademy.shwop.ui.main.fragments.SettingsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class ProfileFragment : BasePostFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override val basePostViewModel: BasePostViewModel
        get() {
            val vm: ProfileViewModel by viewModels()
            return vm
        }

    protected val viewModel: ProfileViewModel
        get() = basePostViewModel as ProfileViewModel

    protected open val uid: String
        get() = FirebaseAuth.getInstance().uid!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToObservers()

        binding.btnToggleFollow.isVisible = false
        viewModel.loadProfile(uid)

        lifecycleScope.launch {
            viewModel.getPagingFlow(uid).collect {
                postAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            postAdapter.loadStateFlow.collectLatest {
                binding.profilePostsProgressBar?.isVisible = it.refresh is LoadState.Loading ||
                        it.append is LoadState.Loading
            }
        }
        binding.btnSetting.setOnClickListener {
            val action = SettingsFragmentDirections.actionGlobalSettingsFragment()
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
        }
    }

    private fun setupRecyclerView() = binding.rvPosts.apply {
        adapter = postAdapter
        itemAnimator = null
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObservers() {
        viewModel.profileMeta.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.profileMetaProgressBar.isVisible = false
                snackbar(it)
            },
            onLoading = { binding.profileMetaProgressBar.isVisible = true }
        ) { user ->
            binding.profileMetaProgressBar.isVisible = false
            binding.tvUsername.text = user.username
            binding.tvProfileDescription.text = user.description
            glide.load(user.profilePictureUrl).into(binding.ivProfileImage)
        })
        basePostViewModel.deletePostStatus.observe(viewLifecycleOwner, EventObserver(
            onError = { snackbar(it) }
        ) { deletedPost ->
            postAdapter.refresh()
        })
    }
}