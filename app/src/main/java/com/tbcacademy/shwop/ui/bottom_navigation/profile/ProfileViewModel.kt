package com.tbcacademy.shwop.ui.bottom_navigation.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tbcacademy.shwop.data.entities.Post
import com.tbcacademy.shwop.data.entities.User
import com.tbcacademy.shwop.data.pagingsource.ProfilePostsPagingSource
import com.tbcacademy.shwop.utils.Constants.PAGE_SIZE
import com.tbcacademy.shwop.utils.Event
import com.tbcacademy.shwop.utils.Resource
import com.tbcacademy.shwop.repositories.main.MainRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.tbcacademy.shwop.ui.main.viewmodels.BasePostViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : BasePostViewModel(repository, dispatcher) {

    private val _profileMeta = MutableLiveData<Event<Resource<User>>>()
    val profileMeta: LiveData<Event<Resource<User>>> = _profileMeta

    private val _followStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val followStatus: LiveData<Event<Resource<Boolean>>> = _followStatus

    fun getPagingFlow(uid: String): Flow<PagingData<Post>> {
        val pagingSource = ProfilePostsPagingSource(
            FirebaseFirestore.getInstance(),
            uid
        )
        return Pager(PagingConfig(PAGE_SIZE)) {
            pagingSource
        }.flow.cachedIn(viewModelScope)
    }

    fun toggleFollowForUser(uid: String) {
        _followStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.toggleFollowForUser(uid)
            _followStatus.postValue(Event(result))
        }
    }

    fun loadProfile(uid: String) {
        _profileMeta.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.getUser(uid)
            _profileMeta.postValue(Event(result))
        }
    }
}