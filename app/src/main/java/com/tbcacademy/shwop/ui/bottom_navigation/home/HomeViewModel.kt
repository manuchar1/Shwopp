package com.tbcacademy.shwop.ui.bottom_navigation.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tbcacademy.shwop.data.pagingsource.FollowPostsPagingSource
import com.tbcacademy.shwop.utils.Constants.PAGE_SIZE
import com.tbcacademy.shwop.repositories.main.MainRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.tbcacademy.shwop.ui.main.viewmodels.BasePostViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class HomeViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : BasePostViewModel(repository, dispatcher) {

    val pagingFlow = Pager(PagingConfig(PAGE_SIZE)) {
        FollowPostsPagingSource(FirebaseFirestore.getInstance())
    }.flow.cachedIn(viewModelScope)
}