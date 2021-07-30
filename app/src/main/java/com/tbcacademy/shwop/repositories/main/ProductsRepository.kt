package com.tbcacademy.shwop.repositories.main

import com.tbcacademy.shwop.data.entities.Post
import com.tbcacademy.shwop.db.ProductsDatabase
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ProductsRepository @Inject constructor(
    private val db: ProductsDatabase
) {


    suspend fun upsert(post: Post) = db.getProductsDao().upsert(post)

    fun getSavedProducts() = db.getProductsDao().getAllProducts()

    suspend fun deleteProducts(post: Post) = db.getProductsDao().deletePost(post)


}
