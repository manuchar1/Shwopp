package com.tbcacademy.shwop.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tbcacademy.shwop.data.UserPreference
import com.tbcacademy.shwop.databinding.LanguageBottomSheetFragmentBinding

class LanguagePickerBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: LanguageBottomSheetFragmentBinding
 //   lateinit var preference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LanguageBottomSheetFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  preference = UserPreference(requireContext())
        binding.langButtonEng.setOnClickListener {
            DataStore.language = "en"
            dismiss()
            activity?.recreate()
        }
        binding.langButtonGeo.setOnClickListener {
            DataStore.language = "ka"
            dismiss()
            activity?.recreate()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply { (this as BottomSheetDialog).behavior.expandedOffset = 300 }
    }

}