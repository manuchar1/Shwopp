package com.tbcacademy.shwop.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
@Entity(
    tableName = "products"
)
data class Post(
    @PrimaryKey(autoGenerate = true)
    @get:Exclude var idr: Int? = null,
    val id: String = "",
    val authorUid: String = "",
    @get:Exclude var authorUsername: String = "",
    @get:Exclude var authorProfilePictureUrl: String = "",
    val text: String = "",
    val product: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val date: Long = 0L,
    @get:Exclude var isLiked: Boolean = false,
    @get:Exclude var isLiking: Boolean = false,
    var likedBy: List<String> = listOf()

) : Serializable