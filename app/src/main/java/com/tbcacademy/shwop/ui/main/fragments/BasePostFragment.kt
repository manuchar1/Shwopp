package com.tbcacademy.shwop.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.adapters.PostAdapter
import com.tbcacademy.shwop.adapters.UserAdapter
import com.tbcacademy.shwop.ui.main.dialogs.DeletePostDialog
import com.tbcacademy.shwop.ui.main.dialogs.LikedByDialog
import com.tbcacademy.shwop.ui.main.viewmodels.BasePostViewModel
import com.tbcacademy.shwop.base.snackbar
import com.tbcacademy.shwop.utils.EventObserver
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BasePostFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var postAdapter: PostAdapter

    protected abstract val basePostViewModel: BasePostViewModel

    private var curLikedIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()

        postAdapter.setOnLikeClickListener { post, i ->
            curLikedIndex = i
            post.isLiked = !post.isLiked
            basePostViewModel.toggleLikeForPost(post)
        }

        postAdapter.setOnDeletePostClickListener { post ->
            DeletePostDialog().apply {
                setPositiveListener {
                    basePostViewModel.deletePost(post)
                }
            }.show(childFragmentManager, null)
        }

        postAdapter.setOnLikedByClickListener { post ->
            basePostViewModel.getUsers(post.likedBy)
        }

        postAdapter.setOnCommentsClickListener { comment ->
            findNavController().navigate(
                R.id.globalActionToCommentDialog,
                Bundle().apply { putString("postId", comment.id) }
            )
        }
    }


    private fun subscribeToObservers() {
        basePostViewModel.likePostStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                curLikedIndex?.let { index ->
                    postAdapter.peek(index)?.isLiking = false
                    postAdapter.notifyItemChanged(index)
                }
                snackbar(it)
            },
            onLoading = {
                curLikedIndex?.let { index ->
                    postAdapter.peek(index)?.isLiking = true
                    postAdapter.notifyItemChanged(index)
                }
            }
        ) { isLiked ->
            curLikedIndex?.let { index ->
                val uid = FirebaseAuth.getInstance().uid!!
                postAdapter.peek(index)?.apply {
                    this.isLiked = isLiked
                    isLiking = false
                    if(isLiked) {
                        likedBy += uid
                    } else {
                        likedBy -= uid
                    }
                }
                postAdapter.notifyItemChanged(index)
            }
        })
        basePostViewModel.likedByUsers.observe(viewLifecycleOwner, EventObserver(
            onError = { snackbar(it) }
        ) { users ->
            val userAdapter = UserAdapter(glide)
            userAdapter.users = users
            LikedByDialog(userAdapter).show(childFragmentManager, null)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}