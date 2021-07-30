package com.tbcacademy.shwop.ui.main.product_to_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbcacademy.shwop.data.entities.Post
import com.tbcacademy.shwop.repositories.main.ProductsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(

    private val repository: ProductsRepository

)  : ViewModel() {


    fun saveProduct(post: Post) = viewModelScope.launch {
        repository.upsert(post)
    }

    fun getSavedProduct() = repository.getSavedProducts()

    fun deleteProduct(post: Post) = viewModelScope.launch {
        repository.deleteProducts(post)
    }
}