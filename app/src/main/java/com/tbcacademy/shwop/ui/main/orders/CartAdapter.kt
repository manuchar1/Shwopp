package com.tbcacademy.shwop.ui.main.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.tbcacademy.shwop.data.entities.Post
import com.tbcacademy.shwop.databinding.ItemCartBinding
import javax.inject.Inject


class CartAdapter @Inject constructor(
    private val glide: RequestManager

) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val cart = differ.currentList[absoluteAdapterPosition]

            itemView.apply {
                glide.load(cart.imageUrl).into(binding.ivProduct)
                binding.tvName.text = cart.product
                binding.tvPrice.text = cart.price

                setOnClickListener {
                    onItemClickListener?.let { it(cart) }
                }

            }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind()

    }

    private var onItemClickListener: ((Post) -> Unit)? = null

    fun setOnItemClickListener(listener: (Post) -> Unit) {
        onItemClickListener = listener

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}