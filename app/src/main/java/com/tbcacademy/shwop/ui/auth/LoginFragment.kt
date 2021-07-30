package com.tbcacademy.shwop.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.razir.progressbutton.hideProgress
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.base.showProgressButton
import com.tbcacademy.shwop.data.UserPreference
import com.tbcacademy.shwop.databinding.LoginFragmentBinding
import com.tbcacademy.shwop.utils.EventObserver
import com.tbcacademy.shwop.base.snackbar

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    val viewModel: AuthViewModel by activityViewModels()
    lateinit var sharedPreferences: UserPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spannableSentence()


        subscribeToObservers()

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }


    private fun subscribeToObservers() {
        viewModel.loginStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.btnLogin.hideProgress(R.string.login)
                snackbar(it)
            },
             onLoading = { showProgressButton(binding.btnLogin)},

        ) {
            binding.btnLogin.hideProgress(R.string.login)

            sharedPreferences = UserPreference(requireContext())
            sharedPreferences.saveUserSession(true)
            sharedPreferences.saveToken("login")
            findNavController().navigate(R.id.action_loginFragment_to_navHomeFragment)

        })


    }

    private fun spannableSentence() {
        val spannable = SpannableString(getString(R.string.if_not_account_register))
        val fcsBlue = ForegroundColorSpan(Color.BLUE)
        spannable.setSpan(fcsBlue, 10, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        val clickable: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        spannable.setSpan(clickable, 10, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvGoAndRegister.movementMethod = LinkMovementMethod.getInstance()
        binding.tvGoAndRegister.text = spannable

    }


}