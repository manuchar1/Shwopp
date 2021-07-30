package com.tbcacademy.shwop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwop.data.entities.Comment
import com.tbcacademy.shwop.databinding.ItemCommentBinding
import javax.inject.Inject

class CommentAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val comment = comments[absoluteAdapterPosition]
            itemView.apply {
                glide.load(comment.profilePictureUrl).into(binding.ivCommentUserProfilePicture)
                binding.ibDeleteComment.isVisible = comment.uid == FirebaseAuth.getInstance().uid!!
                binding.tvComment.text = comment.comment
                binding.tvCommentUsername.text = comment.username

                binding.tvCommentUsername.setOnClickListener {
                    onUserClickListener?.let { click ->
                        click(comment)
                    }
                }
                binding.ibDeleteComment.setOnClickListener {
                    onDeleteCommentClickListener?.let { click ->
                        click(comment)
                    }
                }
            }

        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Comment>() {
        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.commentId == newItem.commentId
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var comments: List<Comment>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentViewHolder(
        ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    )

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind()
    }

    private var onUserClickListener: ((Comment) -> Unit)? = null
    private var onDeleteCommentClickListener: ((Comment) -> Unit)? = null

    fun setOnUserClickListener(listener: (Comment) -> Unit) {
        onUserClickListener = listener
    }

    fun setOnDeleteCommentClickListener(listener: (Comment) -> Unit) {
        onDeleteCommentClickListener = listener
    }

}