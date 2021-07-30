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
import com.tbcacademy.shwop.databinding.RegisterFragmentBinding
import com.tbcacademy.shwop.utils.EventObserver
import com.tbcacademy.shwop.base.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>(RegisterFragmentBinding::inflate) {


    val viewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spannableSentence()
        subscribeToObservers()

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.etSingUpEmail.text.toString(),
                binding.etName.text.toString(),
                binding.etSingUpPassword.text.toString(),
                binding.etSingUpRePassword.text.toString()

            )
        }

    }

    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.btnRegister.hideProgress(R.string.register)
                snackbar(it)

            },
            onLoading = {
                showProgressButton(binding.btnRegister)
                snackbar(getString(R.string.success_registration))
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

            }

        ) {
            binding.btnRegister.hideProgress(R.string.register)
            snackbar(getString(R.string.success_registration))
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        })
    }

    private fun spannableSentence() {
        val spannable = SpannableString(getString(R.string.if_already_a_member))
        val fcsBlue = ForegroundColorSpan(Color.BLUE)
        spannable.setSpan(fcsBlue, 17, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        val clickable: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
        spannable.setSpan(clickable, 17, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvGoToLogin.movementMethod = LinkMovementMethod.getInstance()
        binding.tvGoToLogin.text = spannable

    }

}