package com.tbcacademy.shwop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.data.entities.Post
import com.tbcacademy.shwop.databinding.ItemPostBinding

import javax.inject.Inject

class PostAdapter @Inject constructor(
    private val glide: RequestManager
) : PagingDataAdapter<Post, PostAdapter.ViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val post = getItem(absoluteAdapterPosition) ?: return
            itemView.apply {
                glide.load(post.imageUrl).into(binding.ivImage)
                glide.load(post.authorProfilePictureUrl).into(binding.ivPostAuthor)
                binding.tvAuthorName.text = post.authorUsername
                binding.tvProductType.text = post.product
                binding.tvPrice.text = "₾ ${post.price}"
                binding.tvDetails.text = post.text
                val likeCounter = post.likedBy.size
                binding.tvLiked.text = when {
                    likeCounter <= 0 -> "No likes"
                    else -> "$likeCounter"
                }
                val uid = FirebaseAuth.getInstance().uid!!
                binding.btnDeleteOwnPost.isVisible = uid == post.authorUid
                binding.btnLike.setImageResource(
                    if (post.isLiked) {
                        R.drawable.ic_bold_heart
                    }else{
                        R.drawable.ic_heart
                    }
                )
                binding.apply {
                    tvAuthorName.setOnClickListener {
                        onUserClickListener?.let { click ->
                            click(post.authorUid)

                        }
                    }
                    ivPostAuthor.setOnClickListener {
                        onUserClickListener?.let { click ->
                            click(post.authorUid)

                        }
                    }
                    tvLiked.setOnClickListener {
                        onLikedByClickListener?.let { click ->
                            click(post)

                        }
                    }
                    btnLike.setOnClickListener {
                        onLikeClickListener?.let { click ->
                            if (!post.isLiking)
                            click(post, layoutPosition)

                        }
                    }
                    btnComments.setOnClickListener {
                        onCommentsClickListener?.let { click ->
                            click(post)

                        }
                    }
                    btnDeleteOwnPost.setOnClickListener {
                        onDeletePostClickListener?.let { click ->
                            click(post)

                        }
                    }

                    setOnClickListener {
                        onPostClickListener?.let { it(post) }
                    }

                    btnAddToCart.setOnClickListener {
                        onCartClickListener?.let { it(post) }

                    }

                }

            }


        }


    }




    private var onCartClickListener: ((Post) -> Unit)? = null
    private var onLikeClickListener: ((Post, Int) -> Unit)? = null
    private var onUserClickListener: ((String) -> Unit)? = null
    private var onDeletePostClickListener: ((Post) -> Unit)? = null
    private var onLikedByClickListener: ((Post) -> Unit)? = null
    private var onCommentsClickListener: ((Post) -> Unit)? = null
    private var onPostClickListener: ((Post) -> Unit)? = null


    fun setOnCartClickListener(listener: (Post) -> Unit) {
        onCartClickListener = listener
    }

    fun setOnPostClickListener(listener: (Post) -> Unit) {
        onPostClickListener = listener

    }


    fun setOnLikeClickListener(listener: (Post, Int) -> Unit) {
        onLikeClickListener = listener
    }


    fun setOnUserClickListener(listener: (String) -> Unit) {
        onUserClickListener = listener
    }

    fun setOnDeletePostClickListener(listener: (Post) -> Unit) {
        onDeletePostClickListener = listener
    }

    fun setOnLikedByClickListener(listener: (Post) -> Unit) {
        onLikedByClickListener = listener
    }

    fun setOnCommentsClickListener(listener: (Post) -> Unit) {
        onCommentsClickListener = listener
    }


    companion object: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

    }

}