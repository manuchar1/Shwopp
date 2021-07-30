package com.tbcacademy.shwop.ui.main.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbcacademy.shwop.data.entities.Post
import com.tbcacademy.shwop.repositories.main.ProductsRepository
import kotlinx.coroutines.launch


class DetailsViewModel @ViewModelInject constructor(
    private val repository: ProductsRepository,



) : ViewModel() {

    fun saveProduct(post: Post) = viewModelScope.launch {
        repository.upsert(post)
    }

    fun getSavedProduct() = repository.getSavedProducts()

    fun deleteProduct(post: Post) = viewModelScope.launch {
        repository.deleteProducts(post)
    }


}
