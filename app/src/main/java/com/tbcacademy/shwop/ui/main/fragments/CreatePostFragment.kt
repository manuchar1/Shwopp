package com.tbcacademy.shwop.ui.main.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.github.razir.progressbutton.hideProgress
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.BaseFragment
import com.tbcacademy.shwop.base.showProgressButton
import com.tbcacademy.shwop.databinding.CreatePostFragmentBinding
import com.tbcacademy.shwop.ui.main.viewmodels.CreatePostViewModel
import com.tbcacademy.shwop.base.snackbar
import com.tbcacademy.shwop.utils.EventObserver
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostFragment :
    BaseFragment<CreatePostFragmentBinding>(CreatePostFragmentBinding::inflate) {

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: CreatePostViewModel by viewModels()

    private lateinit var cropContent: ActivityResultLauncher<Any?>

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16, 9)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(requireContext())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    private var curImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cropContent = registerForActivityResult(cropActivityResultContract) {
            it?.let {
                viewModel.setCurImageUri(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()

        binding.ivCamera.setOnClickListener {
            pickImage()
        }
        binding.ivSelect.setOnClickListener {
            cropContent.launch(null)
        }
        binding.ivSelect.setOnClickListener {
            cropContent.launch(null)
        }
        binding.btnPublish.setOnClickListener {
            curImageUri?.let { uri ->
                viewModel.createPost(
                    uri, binding.etDescription.text.toString(),
                    binding.etProduct.text.toString(), binding.etPrice.text.toString()
                )
            } ?: snackbar(getString(R.string.error_no_image_chosen))
        }


    }

    private fun pickImage() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED ) {
            cropContent.launch(null)

            /* val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
             startActivityForResult(galleryIntent,222)*/
        }else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),112)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 112) {
            cropContent.launch(null)
        }else {
            snackbar("Ooops, you just denied the permission for storage. You can also allow it from the settings")
        }

    }

    private fun subscribeToObservers() {
        viewModel.curImageUri.observe(viewLifecycleOwner) {
            curImageUri = it
            binding.ivCamera.isVisible = false
            glide.load(curImageUri).into(binding.ivSelect)
        }
        viewModel.createPostStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.btnPublish.hideProgress(R.string.publish)
                snackbar(it)
            },
            onLoading = { showProgressButton(binding.btnPublish) }
        ) {
            binding.btnPublish.hideProgress(R.string.publish)
            findNavController().popBackStack()
        })
    }
}