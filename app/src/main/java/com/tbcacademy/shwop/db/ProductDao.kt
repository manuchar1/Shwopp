package com.tbcacademy.shwop.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tbcacademy.shwop.data.entities.Post

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(posts: Post): Long

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Post>>

    @Delete
    suspend fun deletePost(post: Post)

}