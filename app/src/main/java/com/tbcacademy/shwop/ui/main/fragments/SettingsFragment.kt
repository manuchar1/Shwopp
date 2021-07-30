package com.tbcacademy.shwop.ui.main.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.RequestManager
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.data.entities.ProfileUpdate
import com.tbcacademy.shwop.utils.EventObserver
import com.tbcacademy.shwop.ui.main.viewmodels.SettingsViewModel
import com.tbcacademy.shwop.base.slideUpViews
import com.tbcacademy.shwop.base.snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.databinding.FragmentSettingsBinding
import com.tbcacademy.shwop.ui.auth.LoginFragmentDirections
import com.tbcacademy.shwop.utils.LanguagePickerBottomSheet
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: SettingsViewModel by viewModels()

    private var curImageUri: Uri? = null

    private lateinit var cropContent: ActivityResultLauncher<Any?>

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(requireContext())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cropContent = registerForActivityResult(cropActivityResultContract) { uri ->
            uri?.let {
                viewModel.setCurImageUri(it)
                btnUpdateProfile.isEnabled = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        val uid = FirebaseAuth.getInstance().uid!!
        viewModel.getUser(uid)
        btnUpdateProfile.isEnabled = false
        binding.etUsername.addTextChangedListener {
            btnUpdateProfile.isEnabled = true
        }
        binding.etDescription.addTextChangedListener {
            btnUpdateProfile.isEnabled = true
        }

        binding.ivProfileImage.setOnClickListener {
            cropContent.launch(null)
        }

        binding.btnUpdateProfile.setOnClickListener {
            val username = etUsername.text.toString()
            val description = etDescription.text.toString()
            val profileUpdate = ProfileUpdate(uid, username, description, curImageUri)
            viewModel.updateProfile(profileUpdate)
        }

        slideUpViews(
            requireContext(),
            ivProfileImage,
            etUsername,
            etDescription,
            btnUpdateProfile,
            btnLogOut,
            tvLogout,
            tvLanguage,
            ivGlobe
        )

        binding.tvLogout.setOnClickListener {
            //findNavController().navigate(R.id.action_settingsFragment2_to_loginFragment)
            val action = LoginFragmentDirections.actionGlobalLoginFragment()
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
        }
        binding.tvLanguage.setOnClickListener {
            val languagePickerBottomSheet = LanguagePickerBottomSheet()
            languagePickerBottomSheet.show(childFragmentManager, "tag")

        }
    }

    private fun subscribeToObservers() {
        viewModel.getUserStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                settingsProgressBar.isVisible = false
                snackbar(it)
            },
            onLoading = { settingsProgressBar.isVisible = true }
        ) { user ->
            settingsProgressBar.isVisible = false
            glide.load(user.profilePictureUrl).into(ivProfileImage)
            etUsername.setText(user.username)
            etDescription.setText(user.description)
            btnUpdateProfile.isEnabled = false
        })
        viewModel.curImageUri.observe(viewLifecycleOwner) { uri ->
            curImageUri = uri
            glide.load(uri).into(ivProfileImage)
        }
        viewModel.updateProfileStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                settingsProgressBar.isVisible = false
                snackbar(it)
                btnUpdateProfile.isEnabled = true
            },
            onLoading = {
                settingsProgressBar.isVisible = true
                btnUpdateProfile.isEnabled = false
            }
        ) {
            settingsProgressBar.isVisible = false
            btnUpdateProfile.isEnabled = false
            snackbar(requireContext().getString(R.string.profile_updated))
        })
    }
}